package cu.osmel.tvplus.ui.view.channel

import android.app.Activity
import android.content.Intent
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.AddPhotoAlternate
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Upload
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import cu.osmel.tvplus.R
import cu.osmel.tvplus.domain.model.Channel
import cu.osmel.tvplus.ui.componets.CustomFloatingButton
import cu.osmel.tvplus.ui.componets.input.InputWrapper
import cu.osmel.tvplus.ui.componets.list.ListEmpty
import cu.osmel.tvplus.ui.componets.list.SimpleItemWithLeftImage
import cu.osmel.tvplus.ui.componets.menu.MenuItem
import cu.osmel.tvplus.ui.componets.searchview.SearchView
import cu.osmel.tvplus.ui.componets.topbar.ActionItem
import cu.osmel.tvplus.ui.componets.util.decodeImageBase64
import cu.osmel.tvplus.ui.componets.util.encodeImageBase64
import cu.osmel.tvplus.ui.navegation.AppView
import cu.osmel.tvplus.ui.view.channel.event.ChannelEvent
import cu.osmel.tvplus.ui.viewmodel.channel.ChannelViewModel
import cu.osmel.tvplus.ui.viewmodel.common.MainViewModel
import kotlinx.coroutines.launch
import java.io.InputStream
import kotlin.math.roundToInt


/**
 * Created by Osmel Pérez Alzola(osmelpa86@gmail.com) on 29/5/22
 */
@ExperimentalComposeUiApi
@Composable
fun ChannelView(
    darkMode: MutableState<Boolean>,
    viewModel: MainViewModel,
    channelViewModel: ChannelViewModel = hiltViewModel(),
) {
    viewModel.setCurrentScreen(AppView.ChannelView)
    viewModel.setCurrentAppBarActions(emptyList())
    var openAddChannelDialog by remember { mutableStateOf(false) }
    val state = channelViewModel.state.value
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val name: InputWrapper by channelViewModel.name.collectAsState()
    val areInputsValid by channelViewModel.areInputsValid.collectAsState()
    val context = LocalContext.current

    val resultImportLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (result.data !== null) {
                    val uriImport: Uri? = result.data!!.data
                    val inputStream: InputStream? =
                        context.contentResolver
                            .openInputStream(uriImport!!)
                    val input = inputStream?.bufferedReader().use { it?.readText() }
                    val gson = Gson()
                    val listChannelType = object : TypeToken<List<Channel>>() {}.type
                    val channels: List<Channel> = gson.fromJson(input, listChannelType)
                    channelViewModel.onEvent(ChannelEvent.InsertAllChannel(channels))
                }
            }
        }

    viewModel.setCurrentAppBarActions(
        listOf(
            ActionItem(
                icon = Icons.Rounded.Upload,
                onClick = {
                    val intent =
                        Intent(Intent.ACTION_OPEN_DOCUMENT)
                    intent.addCategory(Intent.CATEGORY_OPENABLE)
                    intent.type = "*/*"
                    resultImportLauncher.launch(intent)
                }
            ),
        )
    )

    //Show/Hide Fab on list scroll
    val fabHeight = 72.dp
    val fabHeightPx = with(
        LocalDensity.current
    ) {
        fabHeight.roundToPx().toFloat()
    }
    val fabOffsetHeightPx = remember { mutableStateOf(0f) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = fabOffsetHeightPx.value + delta
                fabOffsetHeightPx.value = newOffset.coerceIn(-fabHeightPx, 0f)
                return Offset.Zero
            }
        }
    }

    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        channelViewModel.imageUri = uri
    }

    val uploadMenuitems = listOf(
        MenuItem.Option(
            name = R.string.cancel,
            icon = Icons.Rounded.Clear,
            itemClick = { channelViewModel.cancelSelectImage() }
        ),
        MenuItem.Option(
            name = R.string.select,
            icon = Icons.Rounded.AddPhotoAlternate,
            itemClick = { launcher.launch("image/*") }
        ),
    )

    Scaffold(
        modifier = Modifier
            .nestedScroll(nestedScrollConnection),
        floatingActionButton = {
            CustomFloatingButton(
                onClick = {
                    openAddChannelDialog = true
                },
                icon = Icons.Rounded.Add,
                modifier = Modifier
                    .offset {
                        IntOffset(x = 0, y = -fabOffsetHeightPx.value.roundToInt())
                    }
            )
        },
        isFloatingActionButtonDocked = false,
        floatingActionButtonPosition = FabPosition.End,
        content = {
            ChannelViewContent(
                modifier = Modifier.padding(it),
                onDelete = {
                    scope.launch {
                        channelViewModel.onEvent(ChannelEvent.DeleteChannel(it))
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = context.getString(R.string.success_delete),
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                },
                onEdit = { channel ->
                    channelViewModel.edit = true
                    if (channel != null) {
                        channelViewModel.channelModel = channel
                    }
                    channelViewModel.onNameEntered(input = channel!!.name)
                    channelViewModel.bitmap =
                        if (channel.image !== null && channel.image.isNotEmpty()) decodeImageBase64(channel.image) else null
                    openAddChannelDialog = true
                },
                listChannel = state.listChannels,
                textSearch = channelViewModel.textSearchState,
                channelViewModel = channelViewModel
            )
        }
    )
    ManageChannelView(
        channelViewModel = channelViewModel,
        showDialog = openAddChannelDialog,
        dismissDialog = {
            openAddChannelDialog = false
            channelViewModel.reset()
        },
        successDialog = {
            val image: String? = channelViewModel.image.ifEmpty {
                null
            }

            if (!channelViewModel.edit) {
                channelViewModel.onEvent(
                    ChannelEvent.InsertChannel(
                        Channel(
                            name = channelViewModel.name.value.value,
                            image = image
                        )
                    )
                )
                openAddChannelDialog = false
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = context.getString(R.string.success_insert),
                        duration = SnackbarDuration.Short
                    )
                }
            } else {
                channelViewModel.onEvent(
                    ChannelEvent.UpdateEvent(
                        Channel(
                            id = channelViewModel.channelModel.id,
                            name = channelViewModel.name.value.value,
                            image = image
                        )
                    )
                )
                openAddChannelDialog = false
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = context.getString(R.string.success_update),
                        duration = SnackbarDuration.Short
                    )
                }
            }
        },
        snackbarHostState = snackbarHostState,
        areInputsValid = areInputsValid,
        name = name,
        onSelect = {
            launcher.launch("image/*")
        },
        bitmap = channelViewModel.bitmap,
        options = uploadMenuitems
    )

    channelViewModel.imageUri.let {
        val uri = it
        if (uri != null) {
            if (Build.VERSION.SDK_INT < 28) {
                channelViewModel.bitmap =
                    MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
                channelViewModel.image = encodeImageBase64(uri, context)
            } else {
                val source = ImageDecoder
                    .createSource(context.contentResolver, uri)
                channelViewModel.bitmap = ImageDecoder.decodeBitmap(source)
                channelViewModel.image = encodeImageBase64(uri, context)
            }
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun ChannelViewContent(
    modifier: Modifier = Modifier,
    onDelete: (channel: Channel) -> Unit,
    onEdit: (channel: Channel?) -> Unit,
    listChannel: List<Channel> = emptyList(),
    textSearch: String,
    channelViewModel: ChannelViewModel,
) {
    val scrollState = rememberLazyListState()
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Surface(
        color = MaterialTheme.colors.surface,
        modifier = modifier
    )
    {
        Column {
            SearchView(
                value = textSearch,
                focusRequester = focusRequester,
                onValueChange = {
                    channelViewModel.onSearchTextChanged(it)
                },
                onCancel = {
                    channelViewModel.onCancelSearch()
                    focusManager.clearFocus()
                }
            )
            if (listChannel.isEmpty())
                ListEmpty()
            else {
                LazyColumn(
                    state = scrollState
                ) {
                    items(listChannel) { channel ->
                        SimpleItemWithLeftImage(
                            text = channel.name,
                            bitmap = channel.image,
                            onEdit = { onEdit(channel) },
                            onDelete = { onDelete(channel) },
                            type = R.string.the_channel
                        )
                    }
                }
            }
        }
    }
}