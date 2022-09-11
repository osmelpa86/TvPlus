package cu.osmel.tvplus.ui.viewmodel.tvshow

import android.database.SQLException
import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.ui.text.AnnotatedString
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cu.osmel.tvplus.data.database.entities.*
import cu.osmel.tvplus.domain.mappers.channel.toDomain
import cu.osmel.tvplus.domain.mappers.country.toDomain
import cu.osmel.tvplus.domain.mappers.country.toExposedDropdownItem
import cu.osmel.tvplus.domain.mappers.cover.toSliderItem
import cu.osmel.tvplus.domain.mappers.genre.toChipItem
import cu.osmel.tvplus.domain.mappers.genre.toDomain
import cu.osmel.tvplus.domain.mappers.season.toDatabase
import cu.osmel.tvplus.domain.mappers.season.toDomain
import cu.osmel.tvplus.domain.mappers.tvshow.toDatabase
import cu.osmel.tvplus.domain.mappers.tvshow.toDomain
import cu.osmel.tvplus.domain.model.Channel
import cu.osmel.tvplus.domain.model.Season
import cu.osmel.tvplus.domain.model.TvShow
import cu.osmel.tvplus.domain.usecase.channel.GetAllChannelUseCase
import cu.osmel.tvplus.domain.usecase.country.GetAllCountryUseCase
import cu.osmel.tvplus.domain.usecase.genre.GetAllGenreUseCase
import cu.osmel.tvplus.domain.usecase.season.GetAllSeasonUseCase
import cu.osmel.tvplus.domain.usecase.season.UpdateSeasonUseCase
import cu.osmel.tvplus.domain.usecase.tvshow.*
import cu.osmel.tvplus.ui.componets.input.InputWrapper
import cu.osmel.tvplus.ui.componets.input.chip.ChipItem
import cu.osmel.tvplus.ui.componets.input.exposeddropdownmenu.ExposedDropdownItem
import cu.osmel.tvplus.ui.componets.slider.SliderItem
import cu.osmel.tvplus.ui.componets.util.LogTags
import cu.osmel.tvplus.ui.componets.util.getStateFlow
import cu.osmel.tvplus.ui.view.channel.state.ChannelState
import cu.osmel.tvplus.ui.view.country.state.CountryState
import cu.osmel.tvplus.ui.view.genre.state.GenreState
import cu.osmel.tvplus.ui.view.season.state.SeasonState
import cu.osmel.tvplus.ui.view.tvshow.event.TvShowEvent
import cu.osmel.tvplus.ui.view.tvshow.state.TvShowState
import cu.osmel.tvplus.ui.view.tvshow.validator.TvShowInputValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Osmel Pérez Alzola(osmelpa86@gmail.com) on 7/4/22
 */

@HiltViewModel
class TvShowViewModel @Inject constructor(
    handle: SavedStateHandle,
    private val insertTvShowUseCase: InsertTvShowUseCase,
    private val getAllTvShowUseCase: GetAllTvShowUseCase,
    getAllCountryUseCase: GetAllCountryUseCase,
    getAllChannelUseCase: GetAllChannelUseCase,
    getAllGenreUseCase: GetAllGenreUseCase,
    getAllSeasonUseCase: GetAllSeasonUseCase,
    private val insertTvShowGenreUseCase: InsertTvShowGenreUseCase,
    private val insertCoverUseCase: InsertCoverUseCase,
    private val insertTvShowCoverUseCase: InsertTvShowCoverUseCase,
    private val insertTvShowChannelUseCase: InsertTvShowChannelUseCase,
    private val updateSeasonUseCase: UpdateSeasonUseCase,
    private val getByIdTvShowUseCase: GetByIdTvShowUseCase,
    private val deleteTvShowUseCase:DeleteTvShowUseCase
) : ViewModel() {
    val title = handle.getStateFlow(viewModelScope, "title", InputWrapper())
    val year = handle.getStateFlow(viewModelScope, "year", InputWrapper())
    val country = handle.getStateFlow(viewModelScope, "country", InputWrapper())
    var countrySelected by mutableStateOf(ExposedDropdownItem())
    val duration = handle.getStateFlow(viewModelScope, "duration", InputWrapper())
    val type = handle.getStateFlow(viewModelScope, "type", InputWrapper())
    var typeSelected by mutableStateOf(ExposedDropdownItem())
    val status = handle.getStateFlow(viewModelScope, "status", InputWrapper())
    var statusSelected by mutableStateOf(ExposedDropdownItem())
    val synopsis = handle.getStateFlow(viewModelScope, "synopsis", InputWrapper())
    val listTypes = listOf(
        ExposedDropdownItem(id = 1, text = "Serie"),
        ExposedDropdownItem(id = 2, text = "Novela")
    )
    val listStatus = listOf(
        ExposedDropdownItem(id = 1, text = "Transmisión"),
        ExposedDropdownItem(id = 2, text = "Receso"),
        ExposedDropdownItem(id = 3, text = "Finalizada"),
        ExposedDropdownItem(id = 4, text = "Cancelada")
    )
    val areInputsValid = combine(
        title,
        year,
        country,
        duration
    ) { title, year, country, duration ->
        title.value.isNotEmpty() && title.errorId == null &&
                year.value.isNotEmpty() && year.errorId == null &&
                country.value.isNotEmpty() && country.errorId == null &&
                duration.value.isNotEmpty() && duration.errorId == null
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    val areInputsValidExtra = combine(type, status, synopsis) { type, status, synopsis ->
        type.value.isNotEmpty() && type.errorId == null &&
                status.value.isNotEmpty() && status.errorId == null &&
                synopsis.value.isNotEmpty() && synopsis.errorId == null
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    private val isLoading = MutableLiveData<Boolean>()

    private val _state = mutableStateOf(TvShowState())
    val state: State<TvShowState> = _state
    private val _stateChannel = mutableStateOf(ChannelState())
    val stateChannel: State<ChannelState> = _stateChannel
    private val _stateCountry = mutableStateOf(CountryState())
    val stateCountry: State<CountryState> = _stateCountry
    private val _stateGenre = mutableStateOf(GenreState())
    val stateGenre: State<GenreState> = _stateGenre
    private val _stateSeason = mutableStateOf(SeasonState())
    val stateSeason: State<SeasonState> = _stateSeason
    var selectedGenres by mutableStateOf(mutableListOf<ChipItem>())
    var selectedFronts: MutableList<SliderItem> = mutableStateListOf()
    var selectedSeasons: MutableList<Season> = mutableStateListOf()
    var selectedChannel: MutableList<Channel> = mutableStateListOf()
    var edit by mutableStateOf(false)

    val textSearchState = mutableStateOf("")

    init {
        isLoading.postValue(true)
        getAllTvShowUseCase().onEach { shows ->
            _state.value = state.value.copy(
                listTvShows = shows.map { it.toDomain() }
            )
            isLoading.postValue(false)
        }.launchIn(viewModelScope)

        getAllChannelUseCase().onEach { channels ->
            _stateChannel.value = stateChannel.value.copy(
                listChannels = channels.map { it.toDomain() }
            )
            isLoading.postValue(false)
        }.launchIn(viewModelScope)

        getAllCountryUseCase().onEach { countries ->
            _stateCountry.value = stateCountry.value.copy(
                listCountrys = countries.map { it.toDomain() }
            )
            isLoading.postValue(false)
        }.launchIn(viewModelScope)

        getAllGenreUseCase().onEach { genres ->
            _stateGenre.value = stateGenre.value.copy(
                listGenres = genres.map { it.toDomain() }
            )
            isLoading.postValue(false)
        }.launchIn(viewModelScope)

        getAllSeasonUseCase().onEach { seasons ->
            _stateSeason.value = stateSeason.value.copy(
                listSeasons = seasons.map { it.toDomain() }
            )
            isLoading.postValue(false)
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: TvShowEvent) {
        when (event) {
            is TvShowEvent.InsertTvShow -> {
                viewModelScope.launch {
                    try {
                        isLoading.postValue(true)
                        val tvShowId = insertTvShowUseCase(event.tvShow.toDatabase())
                        if (selectedGenres.isNotEmpty()) {
                            selectedGenres.forEach { genre ->
                                insertTvShowGenreUseCase(
                                    TvShowGenreEntity(
                                        idGenre = genre.id!!,
                                        idTvShow = tvShowId
                                    )
                                )
                            }
                        }

                        if (selectedFronts.isNotEmpty()) {
                            selectedFronts.forEach { cover ->
                                val id = insertCoverUseCase(CoverEntity(cover = cover.imageBase64))
                                insertTvShowCoverUseCase(
                                    TvShowCoverEntity(
                                        idCover = id,
                                        idTvShow = tvShowId
                                    )
                                )
                            }
                        }

                        if (selectedChannel.isNotEmpty()) {
                            selectedChannel.forEach { channel ->
                                insertTvShowChannelUseCase(
                                    TvShowChannelEntity(
                                        idChannel = channel.id!!,
                                        idTvShow = tvShowId
                                    )
                                )
                            }
                        }

                        if (selectedSeasons.isNotEmpty()) {
                            selectedSeasons.forEach { season ->
                                season.idTvShow = tvShowId
                                updateSeasonUseCase(season = season.toDatabase())
                            }
                        }

                        isLoading.postValue(false)
                        reset()
                    } catch (ex: SQLException) {
                        isLoading.postValue(false)
                        Log.e(LogTags.TVSHOW.toString(), ex.message!!)
                    }
                }
            }
            is TvShowEvent.UpdateTvShow -> {
                viewModelScope.launch {
                    println("Entro------------*****************")
                    println(event.tvShow)
                }
            }
            is TvShowEvent.DeleteTvShow -> {
                viewModelScope.launch {
                   deleteTvShowUseCase(event.tvShow.toDatabase())
                }
            }
            is TvShowEvent.LoadByIdTvShow -> {
                viewModelScope.launch {
                    val result = getByIdTvShowUseCase.invoke(id = event.id)?.toDomain()
                    if (result !== null) {
                        onTitleEntered(input = result.serial.title)
                        onYearEntered(input = result.serial.year.toString())
                        onYearEntered(input = result.serial.year.toString())
                        countrySelected = result.country.toExposedDropdownItem()
                        onCountryEntered(result.country.name)
                        onDurationEntered(result.serial.duration)
                        val type = listTypes.find { it.text == result.serial.type }
                        typeSelected =
                            ExposedDropdownItem(id = type!!.id, text = type.text, image = null)
                        onTypeEntered(typeSelected.text.toString())
                        val status = listStatus.find { it.text == result.serial.status }
                        statusSelected = ExposedDropdownItem(
                            id = status!!.id,
                            text = status.text,
                            image = null
                        )
                        onStatusEntered(statusSelected.text.toString())
                        onSynopsisEntered(result.serial.synopsis)
                        selectedGenres = result.genreList.map { it.toChipItem() }.toMutableList()
                        selectedFronts = result.coverList.map { it.toSliderItem() }.toMutableList()
                        selectedSeasons = result.seasonList.toMutableList()
                        selectedChannel = result.channelList.toMutableList()
                    }
                }
            }
        }
    }

    fun onTitleEntered(input: String) {
        viewModelScope.launch {
            val errorId =
                TvShowInputValidator.validateRequired(input)
            title.value = title.value.copy(value = input, errorId = errorId)
        }
    }

    fun onYearEntered(input: String) {
        viewModelScope.launch {
            val errorId =
                TvShowInputValidator.validateRequired(input)
            year.value = year.value.copy(value = input, errorId = errorId)
        }
    }

    fun onCountryEntered(input: String) {
        viewModelScope.launch {
            val errorId =
                TvShowInputValidator.validateRequired(input)
            country.value = country.value.copy(value = input, errorId = errorId)
        }
    }

    fun onDurationEntered(input: String) {
        viewModelScope.launch {
            val errorId =
                TvShowInputValidator.validateRequired(input)
            duration.value = duration.value.copy(value = input, errorId = errorId)
        }
    }

    fun onTypeEntered(input: String) {
        viewModelScope.launch {
            val errorId =
                TvShowInputValidator.validateRequired(input)
            type.value = type.value.copy(value = input, errorId = errorId)
        }
    }

    fun onStatusEntered(input: String) {
        viewModelScope.launch {
            val errorId =
                TvShowInputValidator.validateRequired(input)
            status.value = status.value.copy(value = input, errorId = errorId)
        }
    }

    fun onSynopsisEntered(input: String) {
        viewModelScope.launch {
            val errorId =
                TvShowInputValidator.validateRequired(input)
            synopsis.value = synopsis.value.copy(value = input, errorId = errorId)
        }
    }

    fun updateSelectedSeason(season: Season, remove: Boolean) {
        if (remove) {
            this.selectedSeasons.remove(season)
        } else {
            this.selectedSeasons.add(season)
        }
    }

    fun updateSelectedChannel(channel: Channel, remove: Boolean) {
        if (remove) {
            this.selectedChannel.remove(channel)
        } else {
            this.selectedChannel.add(channel)
        }
    }

    fun reset() {
        viewModelScope.launch {
            title.value = title.value.copy(value = "", errorId = null)
            year.value = year.value.copy(value = "", errorId = null)
            duration.value = duration.value.copy(value = "", errorId = null)
            synopsis.value = duration.value.copy(value = "", errorId = null)

            countrySelected = countrySelected.copy(id = null, text = null, image = null)
            country.value = country.value.copy(value = "", errorId = null)
            typeSelected = typeSelected.copy(id = null, text = null, image = null)
            type.value = type.value.copy(value = "", errorId = null)
            statusSelected = statusSelected.copy(id = null, text = null, image = null)
            status.value = status.value.copy(value = "", errorId = null)
            selectedGenres = mutableStateListOf()
            selectedFronts = mutableStateListOf()
            selectedSeasons = mutableStateListOf()
            selectedChannel = mutableStateListOf()

            edit = false
        }
    }

    fun onSearchTextChanged(changedSearchText: String) {
        viewModelScope.launch {
            textSearchState.value = changedSearchText
            if (changedSearchText.isEmpty()) {
                getAllTvShowUseCase().onEach { shows ->
                    _state.value = state.value.copy(
                        listTvShows = shows.map { it.toDomain() }
                    )
                    isLoading.postValue(false)
                }.launchIn(viewModelScope)
            } else {
                val filteredList = ArrayList<TvShow>()
                state.value.listTvShows.forEach { show ->
                    if (show.serial.title.lowercase() === changedSearchText.lowercase() ||
                        show.serial.year.toString() == changedSearchText ||
                        show.serial.duration.contains(changedSearchText) ||
                        show.country.name.lowercase().contains(changedSearchText.lowercase()) ||
                        show.serial.type.lowercase().contains(changedSearchText.lowercase()) ||
                        show.serial.status.lowercase().contains(changedSearchText.lowercase()) ||
                        show.genreList.any { it.name === changedSearchText.lowercase() } ||
                        show.channelList.any { it.name === changedSearchText.lowercase() }
                    ) {
                        filteredList.add(show)
                    }
                }
                _state.value = state.value.copy(
                    listTvShows = filteredList
                )
            }
        }
    }

    fun onCancelSearch() {
        viewModelScope.launch {
            textSearchState.value = ""
            getAllTvShowUseCase().onEach { shows ->
                _state.value = state.value.copy(
                    listTvShows = shows.map { it.toDomain() }
                )
                isLoading.postValue(false)
            }.launchIn(viewModelScope)
        }
    }
}