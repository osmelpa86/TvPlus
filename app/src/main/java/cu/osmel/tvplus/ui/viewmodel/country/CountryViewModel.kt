package cu.osmel.tvplus.ui.viewmodel.country

import android.database.SQLException
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cu.osmel.tvplus.domain.mappers.country.toDatabase
import cu.osmel.tvplus.domain.mappers.country.toDomain
import cu.osmel.tvplus.domain.model.Country
import cu.osmel.tvplus.domain.usecase.country.*
import cu.osmel.tvplus.ui.componets.input.InputWrapper
import cu.osmel.tvplus.ui.componets.util.LogTags
import cu.osmel.tvplus.ui.componets.util.getStateFlow
import cu.osmel.tvplus.ui.view.country.event.CountryEvent
import cu.osmel.tvplus.ui.view.country.state.CountryState
import cu.osmel.tvplus.ui.view.country.validator.CountryInputValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Osmel Pérez Alzola(osmelpa86@gmail.com) on 29/5/22
 */
@HiltViewModel
class CountryViewModel @Inject constructor(
    private val getAllCountryUseCase: GetAllCountryUseCase,
    private val getCountryByNameUseCase: GetCountryByNameUseCase,
    private val insertCountryUseCase: InsertCountryUseCase,
    private val insertAllCountryUseCase: InsertAllCountryUseCase,
    private val updateCountryUseCase: UpdateCountryUseCase,
    private val deleteCountryUseCase: DeleteCountryUseCase,
    handle: SavedStateHandle,
) : ViewModel() {
    val name = handle.getStateFlow(viewModelScope, "name", InputWrapper())
    var image: String by mutableStateOf("")
    val areInputsValid = combine(name) { name ->
        name[0].value.isNotEmpty() && name[0].errorId == null
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)
    var edit by mutableStateOf(false)

    private val _state = mutableStateOf(CountryState())
    val state: State<CountryState> = _state

    var countryModel by mutableStateOf(Country(id = null, name = "", image = null))

    var textSearchState by mutableStateOf("")

    private val isLoading = MutableLiveData<Boolean>()

    var imageUri by mutableStateOf<Uri?>(null)
    var bitmap by mutableStateOf<Bitmap?>(null)

    init {
        isLoading.postValue(true)
        getAllCountryUseCase().onEach { countrys ->
            _state.value = state.value.copy(
                listCountrys = countrys.map { it.toDomain() }
            )
            isLoading.postValue(false)
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: CountryEvent) {
        when (event) {
            is CountryEvent.InsertCountry -> {
                viewModelScope.launch {
                    try {
                        isLoading.postValue(true)
                        insertCountryUseCase(event.country.toDatabase())
                        isLoading.postValue(false)
                        reset()
                    } catch (ex: SQLException) {
                        isLoading.postValue(false)
                        Log.e(LogTags.GENRE.toString(), ex.message!!)
                    }
                }
            }
            is CountryEvent.InsertAllCountry -> {
                viewModelScope.launch {
                    try {
                        isLoading.postValue(true)
                        insertAllCountryUseCase(event.list.map { it.toDatabase() })
                        isLoading.postValue(false)
                        reset()
                    } catch (ex: SQLException) {
                        isLoading.postValue(false)
                        Log.e(LogTags.GENRE.toString(), ex.message!!)
                    }
                }
            }
            is CountryEvent.UpdateEvent -> {
                viewModelScope.launch {
                    isLoading.postValue(true)
                    updateCountryUseCase(event.country.toDatabase())
                    isLoading.postValue(false)
                    reset()
                }
            }
            is CountryEvent.DeleteCountry -> {
                viewModelScope.launch {
                    isLoading.postValue(true)
                    deleteCountryUseCase(event.country.toDatabase())
                    isLoading.postValue(false)
                }
            }
        }
    }

    fun onNameEntered(input: String) {
        viewModelScope.launch {
            val errorId =
                CountryInputValidator.validate(
                    input,
                    getCountryByNameUseCase,
                    edit = edit,
                    countryModel
                )
            name.value = name.value.copy(value = input, errorId = errorId)
        }
    }

    fun reset() {
        viewModelScope.launch {
            name.value = name.value.copy(value = "", errorId = null)
            edit = false
            image = ""
            imageUri = null
            bitmap = null
            countryModel = Country(id = null, name = "", image = null)
        }
    }

    fun onSearchTextChanged(changedSearchText: String) {
        viewModelScope.launch {
            textSearchState = changedSearchText
            if (changedSearchText.isEmpty()) {
                getAllCountryUseCase().onEach { countrys ->
                    _state.value = state.value.copy(
                        listCountrys = countrys.map { it.toDomain() }
                    )
                    isLoading.postValue(false)
                }.launchIn(viewModelScope)
            } else {
                val filteredList = ArrayList<Country>()
                state.value.listCountrys.forEach { country ->
                    if (country.name.lowercase().contains(changedSearchText.lowercase())) {
                        filteredList.add(country)
                    }
                }
                _state.value = state.value.copy(
                    listCountrys = filteredList
                )
            }
        }
    }

    fun onCancelSearch() {
        viewModelScope.launch {
            textSearchState = ""
            getAllCountryUseCase().onEach { countrys ->
                _state.value = state.value.copy(
                    listCountrys = countrys.map { it.toDomain() }
                )
                isLoading.postValue(false)
            }.launchIn(viewModelScope)
        }
    }

    fun cancelSelectImage() {
        viewModelScope.launch {
            bitmap = null
            image = ""
            imageUri = null
        }
    }
}