package cu.osmel.tvplus.ui.view.season

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
import cu.osmel.tvplus.ui.viewmodel.season.SeasonViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import cu.osmel.tvplus.R
import cu.osmel.tvplus.domain.model.Season
import cu.osmel.tvplus.ui.componets.CustomFloatingButton
import cu.osmel.tvplus.ui.componets.input.InputWrapper
import cu.osmel.tvplus.ui.componets.list.ListEmpty
import cu.osmel.tvplus.ui.componets.list.SeasonItem
import cu.osmel.tvplus.ui.componets.searchview.SearchView
import cu.osmel.tvplus.ui.componets.topbar.ActionItem
import cu.osmel.tvplus.ui.view.season.event.SeasonEvent
import kotlinx.coroutines.launch
import java.io.InputStream
import kotlin.math.roundToInt

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun SeasonView(
    darkMode: MutableState<Boolean>,
    viewModel: MainViewModel,
    seasonViewModel: SeasonViewModel = hiltViewModel(),
) {
    viewModel.setCurrentScreen(AppView.SeasonView)
    viewModel.setCurrentAppBarActions(emptyList())
    var openAddSeasonDialog by remember { mutableStateOf(false) }
    val state = seasonViewModel.state.value
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val idTvShow: InputWrapper by seasonViewModel.idTvShow.collectAsState()
    val number: InputWrapper by seasonViewModel.number.collectAsState()
    val totalChapters: InputWrapper by seasonViewModel.totalChapters.collectAsState()
    val status: InputWrapper by seasonViewModel.status.collectAsState()
    val year: InputWrapper by seasonViewModel.year.collectAsState()

    val areInputsValid by seasonViewModel.areInputsValid.collectAsState()
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
                    val listSeasonType = object : TypeToken<List<Season>>() {}.type
                    val seasons: List<Season> = gson.fromJson(input, listSeasonType)
                    seasonViewModel.onEvent(SeasonEvent.InsertAllSeason(seasons))
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
                    openAddSeasonDialog = true
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
            SeasonViewContent(
                modifier = Modifier.padding(it),
                onDelete = { season ->
                    seasonViewModel.onEvent(SeasonEvent.DeleteSeason(season))
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = context.getString(R.string.success_delete),
                            duration = SnackbarDuration.Short
                        )
                    }
                },
                onEdit = { season ->
                    seasonViewModel.edit.value = true
                    if (season != null) {
                        seasonViewModel.seasonModel.value = season
                    }

                    seasonViewModel.onTvShowEntered(input = season!!.idTvShow.toString())
                    seasonViewModel.onNumberEntered(input = season.number.toString())
                    seasonViewModel.onTotalChaptersEntered(input = season.totalChapters.toString())
                    seasonViewModel.onStatusEntered(input = season.status)
                    seasonViewModel.onYearEntered(input = season.year)
                    openAddSeasonDialog = true
                },
                listSeason = state.listSeasons,
                textSearch = seasonViewModel.textSearchState.value,
                seasonViewModel = seasonViewModel
            )
        }
    )
    ManageSeasonView(
        seasonViewModel = seasonViewModel,
        showDialog = openAddSeasonDialog,
        dismissDialog = {
            openAddSeasonDialog = false
            seasonViewModel.reset()
        },
        successDialog = {
            if (!seasonViewModel.edit.value) {
                seasonViewModel.onEvent(
                    SeasonEvent.InsertSeason(
                        Season(
                            idTvShow = if (seasonViewModel.idTvShow.value.value.isNotEmpty()) seasonViewModel.idTvShow.value.value.toLong() else null,
                            number = seasonViewModel.number.value.value.toInt(),
                            totalChapters = seasonViewModel.totalChapters.value.value.toInt(),
                            status = seasonViewModel.status.value.value,
                            year = seasonViewModel.year.value.value
                        )
                    )
                )
                openAddSeasonDialog = false
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = context.getString(R.string.success_insert),
                        duration = SnackbarDuration.Short
                    )
                }
            } else {
                seasonViewModel.onEvent(
                    SeasonEvent.UpdateEvent(
                        Season(
                            id = seasonViewModel.seasonModel.value.id,
                            idTvShow = seasonViewModel.idTvShow.value.value.toLong(),
                            number = seasonViewModel.number.value.value.toInt(),
                            totalChapters = seasonViewModel.totalChapters.value.value.toInt(),
                            status = seasonViewModel.status.value.value,
                            year = seasonViewModel.year.value.value
                        )
                    )
                )
                openAddSeasonDialog = false
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
        idTvShow = idTvShow,
        number = number,
        totalChapters = totalChapters,
        status = status,
        year = year
    )
}

@ExperimentalComposeUiApi
@Composable
fun SeasonViewContent(
    modifier: Modifier = Modifier,
    onDelete: (season: Season) -> Unit,
    onEdit: (season: Season?) -> Unit,
    listSeason: List<Season> = emptyList(),
    textSearch: String,
    seasonViewModel: SeasonViewModel,
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
                    seasonViewModel.onSearchTextChanged(it)
                },
                onCancel = {
                    seasonViewModel.onCancelSearch()
                    focusManager.clearFocus()
                }
            )
            if (listSeason.isEmpty())
                ListEmpty()
            else {
                LazyColumn(
                    state = scrollState
                ) {
                    items(listSeason) { season ->
                        SeasonItem(
                            season = season,
                            onEdit = { onEdit(season) },
                            onDelete = { onDelete(season) }
                        )
                    }
                }
            }
        }
    }
}