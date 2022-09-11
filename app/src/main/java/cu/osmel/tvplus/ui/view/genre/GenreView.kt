package cu.osmel.tvplus.ui.view.genre

import android.app.Activity
import android.content.Intent
import android.net.Uri
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
import cu.osmel.tvplus.ui.navegation.AppView
import cu.osmel.tvplus.ui.viewmodel.common.MainViewModel
import cu.osmel.tvplus.ui.viewmodel.genre.GenreViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import cu.osmel.tvplus.R
import cu.osmel.tvplus.domain.model.Genre
import cu.osmel.tvplus.ui.componets.CustomFloatingButton
import cu.osmel.tvplus.ui.componets.input.InputWrapper
import cu.osmel.tvplus.ui.componets.list.ListEmpty
import cu.osmel.tvplus.ui.componets.list.SimpleItem
import cu.osmel.tvplus.ui.componets.searchview.SearchView
import cu.osmel.tvplus.ui.componets.topbar.ActionItem
import cu.osmel.tvplus.ui.view.genre.event.GenreEvent
import kotlinx.coroutines.launch
import java.io.InputStream
import kotlin.math.roundToInt

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
@ExperimentalComposeUiApi
@Composable
fun GenreView(
    darkMode: MutableState<Boolean>,
    viewModel: MainViewModel,
    genreViewModel: GenreViewModel = hiltViewModel(),
) {
    viewModel.setCurrentScreen(AppView.GenreView)
    viewModel.setCurrentAppBarActions(emptyList())
    var openAddGenreDialog by remember { mutableStateOf(false) }
    val state = genreViewModel.state.value
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val name: InputWrapper by genreViewModel.name.collectAsState()
    val areInputsValid by genreViewModel.areInputsValid.collectAsState()
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
                    val listGenreType = object : TypeToken<List<Genre>>() {}.type
                    val genres: List<Genre> = gson.fromJson(input, listGenreType)
                    genreViewModel.onEvent(GenreEvent.InsertAllGenre(genres))
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

    Scaffold(
        modifier = Modifier
            .nestedScroll(nestedScrollConnection),
        floatingActionButton = {
            CustomFloatingButton(
                onClick = {
                    openAddGenreDialog = true
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
            GenreViewContent(
                modifier = Modifier.padding(it),
                onDelete = { genre ->
                    genreViewModel.onEvent(GenreEvent.DeleteGenre(genre))
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = context.getString(R.string.success_delete),
                            duration = SnackbarDuration.Short
                        )
                    }
                },
                onEdit = { genre ->
                    genreViewModel.edit.value = true
                    if (genre != null) {
                        genreViewModel.genreModel.value = genre
                    }
                    genreViewModel.onNameEntered(input = genre!!.name)
                    openAddGenreDialog = true
                },
                listGenre = state.listGenres,
                textSearch = genreViewModel.textSearchState.value,
                genreViewModel = genreViewModel
            )
        }
    )
    ManageGenreView(
        genreViewModel = genreViewModel,
        showDialog = openAddGenreDialog,
        dismissDialog = {
            openAddGenreDialog = false
            genreViewModel.reset()
        },
        successDialog = {
            if (!genreViewModel.edit.value) {
                genreViewModel.onEvent(GenreEvent.InsertGenre(Genre(name = genreViewModel.name.value.value)))
                openAddGenreDialog = false
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = context.getString(R.string.success_insert),
                        duration = SnackbarDuration.Short
                    )
                }
            } else {
                genreViewModel.onEvent(
                    GenreEvent.UpdateEvent(
                        Genre(
                            id = genreViewModel.genreModel.value.id,
                            name = genreViewModel.name.value.value
                        )
                    )
                )
                openAddGenreDialog = false
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
        name = name
    )
}

@ExperimentalComposeUiApi
@Composable
fun GenreViewContent(
    modifier: Modifier = Modifier,
    onDelete: (genre: Genre) -> Unit,
    onEdit: (genre: Genre?) -> Unit,
    listGenre: List<Genre> = emptyList(),
    textSearch: String,
    genreViewModel: GenreViewModel,
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
                    genreViewModel.onSearchTextChanged(it)
                },
                onCancel = {
                    genreViewModel.onCancelSearch()
                    focusManager.clearFocus()
                }
            )
            if (listGenre.isEmpty())
                ListEmpty()
            else {
                LazyColumn(
                    state = scrollState
                ) {
                    items(listGenre) { genre ->
                        SimpleItem(
                            text = genre.name,
                            type = R.string.the_genre,
                            onEdit = { onEdit(genre) },
                            onDelete = { onDelete(genre) }
                        )
                    }
                }
            }
        }
    }
}