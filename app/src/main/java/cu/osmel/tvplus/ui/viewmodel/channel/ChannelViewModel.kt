package cu.osmel.tvplus.ui.viewmodel.channel

import android.database.SQLException
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cu.osmel.tvplus.domain.mappers.channel.toDatabase
import cu.osmel.tvplus.domain.mappers.channel.toDomain
import cu.osmel.tvplus.domain.model.Channel
import cu.osmel.tvplus.domain.usecase.channel.*
import cu.osmel.tvplus.ui.componets.input.InputWrapper
import cu.osmel.tvplus.ui.componets.util.LogTags
import cu.osmel.tvplus.ui.componets.util.getStateFlow
import cu.osmel.tvplus.ui.view.channel.event.ChannelEvent
import cu.osmel.tvplus.ui.view.channel.state.ChannelState
import cu.osmel.tvplus.ui.view.channel.validator.ChannelInputValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Osmel Pérez Alzola(osmelpa86@gmail.com) on 29/5/22
 */
@HiltViewModel
class ChannelViewModel @Inject constructor(
    private val getAllChannelUseCase: GetAllChannelUseCase,
    private val getChannelByNameUseCase: GetChannelByNameUseCase,
    private val insertChannelUseCase: InsertChannelUseCase,
    private val insertAllChannelUseCase: InsertAllChannelUseCase,
    private val updateChannelUseCase: UpdateChannelUseCase,
    private val deleteChannelUseCase: DeleteChannelUseCase,
    handle: SavedStateHandle,
) : ViewModel() {
    val name = handle.getStateFlow(viewModelScope, "name", InputWrapper())
    var image: String by mutableStateOf("")
    val areInputsValid = combine(name) { name ->
        name[0].value.isNotEmpty() && name[0].errorId == null
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)
    var edit by mutableStateOf(false)

    private val _state = mutableStateOf(ChannelState())
    val state: State<ChannelState> = _state

    var channelModel by mutableStateOf(Channel(id = null, name = "", image = null))

    var textSearchState by mutableStateOf("")

    private val isLoading = MutableLiveData<Boolean>()

    var imageUri by mutableStateOf<Uri?>(null)
    var bitmap by mutableStateOf<Bitmap?>(null)

    init {
        isLoading.postValue(true)
        getAllChannelUseCase().onEach { channels ->
            _state.value = state.value.copy(
                listChannels = channels.map { it.toDomain() }
            )
            isLoading.postValue(false)
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: ChannelEvent) {
        when (event) {
            is ChannelEvent.InsertChannel -> {
                viewModelScope.launch {
                    try {
                        isLoading.postValue(true)
                        insertChannelUseCase(event.channel.toDatabase())
                        isLoading.postValue(false)
                        reset()
                    } catch (ex: SQLException) {
                        isLoading.postValue(false)
                        Log.e(LogTags.GENRE.toString(), ex.message!!)
                    }
                }
            }
            is ChannelEvent.InsertAllChannel -> {
                viewModelScope.launch {
                    try {
                        isLoading.postValue(true)
                        insertAllChannelUseCase(event.list.map { it.toDatabase() })
                        isLoading.postValue(false)
                        reset()
                    } catch (ex: SQLException) {
                        isLoading.postValue(false)
                        Log.e(LogTags.GENRE.toString(), ex.message!!)
                    }
                }
            }
            is ChannelEvent.UpdateEvent -> {
                viewModelScope.launch {
                    isLoading.postValue(true)
                    updateChannelUseCase(event.channel.toDatabase())
                    isLoading.postValue(false)
                    reset()
                }
            }
            is ChannelEvent.DeleteChannel -> {
                viewModelScope.launch {
                    isLoading.postValue(true)
                    deleteChannelUseCase(event.channel.toDatabase())
                    isLoading.postValue(false)
                }
            }
        }
    }

    fun onNameEntered(input: String) {
        viewModelScope.launch {
            val errorId =
                ChannelInputValidator.validate(
                    input,
                    getChannelByNameUseCase,
                    edit = edit,
                    channelModel
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
            channelModel = Channel(id = null, name = "", image = null)
        }
    }

    fun onSearchTextChanged(changedSearchText: String) {
        viewModelScope.launch {
            textSearchState = changedSearchText
            if (changedSearchText.isEmpty()) {
                getAllChannelUseCase().onEach { channels ->
                    _state.value = state.value.copy(
                        listChannels = channels.map { it.toDomain() }
                    )
                    isLoading.postValue(false)
                }.launchIn(viewModelScope)
            } else {
                val filteredList = ArrayList<Channel>()
                state.value.listChannels.forEach { channel ->
                    if (channel.name.lowercase().contains(changedSearchText.lowercase())) {
                        filteredList.add(channel)
                    }
                }
                _state.value = state.value.copy(
                    listChannels = filteredList
                )
            }
        }
    }

    fun onCancelSearch() {
        viewModelScope.launch {
            textSearchState = ""
            getAllChannelUseCase().onEach { channels ->
                _state.value = state.value.copy(
                    listChannels = channels.map { it.toDomain() }
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