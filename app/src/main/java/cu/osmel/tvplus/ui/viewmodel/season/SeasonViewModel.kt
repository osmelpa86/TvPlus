package cu.osmel.tvplus.ui.viewmodel.season

import android.database.SQLException
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cu.osmel.tvplus.domain.mappers.season.toDatabase
import cu.osmel.tvplus.domain.mappers.season.toDomain
import cu.osmel.tvplus.domain.model.Season
import cu.osmel.tvplus.domain.usecase.season.*
import cu.osmel.tvplus.ui.componets.input.InputWrapper
import cu.osmel.tvplus.ui.componets.input.exposeddropdownmenu.ExposedDropdownItem
import cu.osmel.tvplus.ui.componets.util.LogTags
import cu.osmel.tvplus.ui.componets.util.getStateFlow
import cu.osmel.tvplus.ui.view.season.event.SeasonEvent
import cu.osmel.tvplus.ui.view.season.state.SeasonState
import cu.osmel.tvplus.ui.view.season.validator.SeasonInputValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Osmel Pérez Alzola(osmelpa86@gmail.com) on 29/5/22
 */
@HiltViewModel
class SeasonViewModel @Inject constructor(
    private val getAllSeasonUseCase: GetAllSeasonUseCase,
    private val insertSeasonUseCase: InsertSeasonUseCase,
    private val insertAllSeasonUseCase: InsertAllSeasonUseCase,
    private val updateSeasonUseCase: UpdateSeasonUseCase,
    private val deleteSeasonUseCase: DeleteSeasonUseCase,
    handle: SavedStateHandle,
) : ViewModel() {

    val idTvShow = handle.getStateFlow(viewModelScope, "idTvShow", InputWrapper())
    val number = handle.getStateFlow(viewModelScope, "number", InputWrapper())
    val totalChapters = handle.getStateFlow(viewModelScope, "totalChapters", InputWrapper())
    val status = handle.getStateFlow(viewModelScope, "status", InputWrapper())
    var statusSelected by mutableStateOf(ExposedDropdownItem())
    val year = handle.getStateFlow(viewModelScope, "year", InputWrapper())

    val areInputsValid =
        combine(number, totalChapters, status, year) { number, totalChapters, status, year ->
            number.value.isNotEmpty() && number.errorId == null &&
                    totalChapters.value.isNotEmpty() && totalChapters.errorId == null &&
                    status.value.isNotEmpty() && status.errorId == null &&
                    year.value.isNotEmpty() && year.errorId == null
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)
    var edit = mutableStateOf(false)

    private val _state = mutableStateOf(SeasonState())
    val state: State<SeasonState> = _state

    var seasonModel = mutableStateOf(
        Season(
            id = null,
            idTvShow = null,
            number = 0,
            totalChapters = 0,
            status = "",
            year = ""
        )
    )

    val textSearchState = mutableStateOf("")

    private val isLoading = MutableLiveData<Boolean>()

    val listStatus = listOf(
        ExposedDropdownItem(id = 1, text = "Transmisión"),
        ExposedDropdownItem(id = 2, text = "Receso"),
        ExposedDropdownItem(id = 3, text = "Finalizada")
    )

    init {
        isLoading.postValue(true)
        getAllSeasonUseCase().onEach { seasons ->
            _state.value = state.value.copy(
                listSeasons = seasons.map { it.toDomain() }
            )
            isLoading.postValue(false)
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: SeasonEvent) {
        when (event) {
            is SeasonEvent.InsertSeason -> {
                viewModelScope.launch {
                    try {
                        isLoading.postValue(true)
                        insertSeasonUseCase(event.season.toDatabase())
                        isLoading.postValue(false)
                        reset()
                    } catch (ex: SQLException) {
                        isLoading.postValue(false)
                        Log.e(LogTags.GENRE.toString(), ex.message!!)
                    }
                }
            }
            is SeasonEvent.InsertAllSeason -> {
                viewModelScope.launch {
                    try {
                        isLoading.postValue(true)
                        insertAllSeasonUseCase(event.list.map { it.toDatabase() })
                        isLoading.postValue(false)
                        reset()
                    } catch (ex: SQLException) {
                        isLoading.postValue(false)
                        Log.e(LogTags.GENRE.toString(), ex.message!!)
                    }
                }
            }
            is SeasonEvent.UpdateEvent -> {
                viewModelScope.launch {
                    isLoading.postValue(true)
                    updateSeasonUseCase(event.season.toDatabase())
                    isLoading.postValue(false)
                    reset()
                }
            }
            is SeasonEvent.DeleteSeason -> {
                viewModelScope.launch {
                    isLoading.postValue(true)
                    deleteSeasonUseCase(event.season.toDatabase())
                    isLoading.postValue(false)
                }
            }
        }
    }

    fun onTvShowEntered(input: String) {
        viewModelScope.launch {
            idTvShow.value = idTvShow.value.copy(value = input, errorId = null)
        }
    }

    fun onNumberEntered(input: String) {
        viewModelScope.launch {
            val errorId =
                SeasonInputValidator.validate(
                    input
                )
            number.value = number.value.copy(value = input, errorId = errorId)
        }
    }

    fun onTotalChaptersEntered(input: String) {
        viewModelScope.launch {
            val errorId =
                SeasonInputValidator.validate(
                    input
                )
            totalChapters.value = totalChapters.value.copy(value = input, errorId = errorId)
        }
    }

    fun onStatusEntered(input: String) {
        viewModelScope.launch {
            val errorId =
                SeasonInputValidator.validate(
                    input
                )
            status.value = status.value.copy(value = input, errorId = errorId)
        }
    }

    fun onYearEntered(input: String) {
        viewModelScope.launch {
            val errorId =
                SeasonInputValidator.validate(
                    input
                )
            year.value = year.value.copy(value = input, errorId = errorId)
        }
    }

    fun reset() {
        viewModelScope.launch {
            number.value = number.value.copy(value = "", errorId = null)
            totalChapters.value = totalChapters.value.copy(value = "", errorId = null)
            status.value = status.value.copy(value = "", errorId = null)
            year.value = year.value.copy(value = "", errorId = null)
            seasonModel.value = Season(
                id = null,
                idTvShow = null,
                number = 0,
                totalChapters = 0,
                status = "",
                year = ""
            )
            edit.value = false
        }
    }

    fun onSearchTextChanged(changedSearchText: String) {
        viewModelScope.launch {
            textSearchState.value = changedSearchText
            if (changedSearchText.isEmpty()) {
                getAllSeasonUseCase().onEach { seasons ->
                    _state.value = state.value.copy(
                        listSeasons = seasons.map { it.toDomain() }
                    )
                    isLoading.postValue(false)
                }.launchIn(viewModelScope)
            } else {
                val filteredList = ArrayList<Season>()
                state.value.listSeasons.forEach { season ->
                    if (season.number === changedSearchText.toInt() ||
                        season.totalChapters === changedSearchText.toInt() ||
                        season.status.lowercase().contains(changedSearchText.lowercase()) ||
                        season.year.lowercase().contains(changedSearchText.lowercase())
                    ) {
                        filteredList.add(season)
                    }
                }
                _state.value = state.value.copy(
                    listSeasons = filteredList
                )
            }
        }
    }

    fun onCancelSearch() {
        viewModelScope.launch {
            textSearchState.value = ""
            getAllSeasonUseCase().onEach { seasons ->
                _state.value = state.value.copy(
                    listSeasons = seasons.map { it.toDomain() }
                )
                isLoading.postValue(false)
            }.launchIn(viewModelScope)
        }
    }
}