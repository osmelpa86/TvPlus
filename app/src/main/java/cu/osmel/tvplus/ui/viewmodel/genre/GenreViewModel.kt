package cu.osmel.tvplus.ui.viewmodel.genre

import android.database.SQLException
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cu.osmel.tvplus.domain.mappers.genre.toDatabase
import cu.osmel.tvplus.domain.mappers.genre.toDomain
import cu.osmel.tvplus.domain.model.Genre
import cu.osmel.tvplus.domain.usecase.genre.*
import cu.osmel.tvplus.ui.componets.input.InputWrapper
import cu.osmel.tvplus.ui.componets.util.LogTags
import cu.osmel.tvplus.ui.componets.util.getStateFlow
import cu.osmel.tvplus.ui.view.genre.event.GenreEvent
import cu.osmel.tvplus.ui.view.genre.state.GenreState
import cu.osmel.tvplus.ui.view.genre.validator.GenreInputValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Osmel Pérez Alzola(osmelpa86@gmail.com) on 7/4/22
 */

@HiltViewModel
class GenreViewModel @Inject constructor(
    private val getAllGenreUseCase: GetAllGenreUseCase,
    private val getGenreByNameUseCase: GetGenreByNameUseCase,
    private val insertGenreUseCase: InsertGenreUseCase,
    private val insertAllGenreUseCase: InsertAllGenreUseCase,
    private val updateGenreUseCase: UpdateGenreUseCase,
    private val deleteGenreUseCase: DeleteGenreUseCase,
    handle: SavedStateHandle,
) : ViewModel() {
    val name = handle.getStateFlow(viewModelScope, "name", InputWrapper())
    val areInputsValid = combine(name) { name ->
        name[0].value.isNotEmpty() && name[0].errorId == null
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)
    var edit = mutableStateOf(false)

    private val _state = mutableStateOf(GenreState())
    val state: State<GenreState> = _state

    var genreModel = mutableStateOf(Genre(id = null, name = ""))

    val textSearchState = mutableStateOf("")

    private val isLoading = MutableLiveData<Boolean>()

    init {
        isLoading.postValue(true)
        getAllGenreUseCase().onEach { genres ->
            _state.value = state.value.copy(
                listGenres = genres.map { it.toDomain() }
            )
            isLoading.postValue(false)
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: GenreEvent) {
        when (event) {
            is GenreEvent.InsertGenre -> {
                viewModelScope.launch {
                    try {
                        isLoading.postValue(true)
                        insertGenreUseCase(event.genre.toDatabase())
                        isLoading.postValue(false)
                        reset()
                    } catch (ex: SQLException) {
                        isLoading.postValue(false)
                        Log.e(LogTags.GENRE.toString(), ex.message!!)
                    }
                }
            }
            is GenreEvent.InsertAllGenre -> {
                viewModelScope.launch {
                    try {
                        isLoading.postValue(true)
                        println(event.list)
                        insertAllGenreUseCase(event.list.map { it.toDatabase() })
                        isLoading.postValue(false)
                        reset()
                    } catch (ex: SQLException) {
                        isLoading.postValue(false)
                        Log.e(LogTags.GENRE.toString(), ex.message!!)
                    }
                }
            }
            is GenreEvent.UpdateEvent -> {
                viewModelScope.launch {
                    isLoading.postValue(true)
                    updateGenreUseCase(event.genre.toDatabase())
                    isLoading.postValue(false)
                    reset()
                }
            }
            is GenreEvent.DeleteGenre -> {
                viewModelScope.launch {
                    isLoading.postValue(true)
                    deleteGenreUseCase(event.genre.toDatabase())
                    isLoading.postValue(false)
                }
            }
        }
    }

    fun onNameEntered(input: String) {
        viewModelScope.launch {
            val errorId =
                GenreInputValidator.validate(
                    input,
                    getGenreByNameUseCase,
                    edit = edit.value,
                    genreModel.value
                )
            name.value = name.value.copy(value = input, errorId = errorId)
        }
    }

    fun reset() {
        viewModelScope.launch {
            name.value = name.value.copy(value = "", errorId = null)
            genreModel.value = Genre(id = null, name = "")
            edit.value = false
        }
    }

    fun onSearchTextChanged(changedSearchText: String) {
        viewModelScope.launch {
            textSearchState.value = changedSearchText
            if (changedSearchText.isEmpty()) {
                getAllGenreUseCase().onEach { genres ->
                    _state.value = state.value.copy(
                        listGenres = genres.map { it.toDomain() }
                    )
                    isLoading.postValue(false)
                }.launchIn(viewModelScope)
            } else {
                val filteredList = ArrayList<Genre>()
                state.value.listGenres.forEach { genre ->
                    if (genre.name.lowercase().contains(changedSearchText.lowercase())) {
                        filteredList.add(genre)
                    }
                }
                _state.value = state.value.copy(
                    listGenres = filteredList
                )
            }
        }
    }

    fun onCancelSearch() {
        viewModelScope.launch {
            textSearchState.value = ""
            getAllGenreUseCase().onEach { genres ->
                _state.value = state.value.copy(
                    listGenres = genres.map { it.toDomain() }
                )
                isLoading.postValue(false)
            }.launchIn(viewModelScope)
        }
    }
}