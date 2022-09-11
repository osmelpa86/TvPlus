package cu.osmel.tvplus.ui.componets.util

import androidx.compose.ui.res.colorResource
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import cu.osmel.tvplus.ui.componets.input.exposeddropdownmenu.ExposedDropdownItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import cu.osmel.tvplus.R

fun <T> SavedStateHandle.getStateFlow(
    scope: CoroutineScope,
    key: String,
    initialValue: T,
): MutableStateFlow<T> {
    val liveData = getLiveData(key, initialValue)
    val stateFlow = MutableStateFlow(initialValue)

    val observer = Observer<T> { value -> if (value != stateFlow.value) stateFlow.value = value }
    liveData.observeForever(observer)

    stateFlow.onCompletion {
        withContext(Dispatchers.Main.immediate) {
            liveData.removeObserver(observer)
        }
    }.onEach { value ->
        withContext(Dispatchers.Main.immediate) {
            if (liveData.value != value) liveData.value = value
        }
    }.launchIn(scope)

    return stateFlow
}

fun manageLength(input: String, length: Int) =
    if (input.length > length) input.substring(0..length - 1) else input

fun statusColor(status: String) = when (status) {
    "Transmisión" -> R.color.transmission
    "Receso" -> R.color.recess
    else -> R.color.finalized
}