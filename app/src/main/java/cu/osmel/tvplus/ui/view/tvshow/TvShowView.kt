package cu.osmel.tvplus.ui.view.tvshow

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import cu.osmel.tvplus.R
import cu.osmel.tvplus.domain.model.TvShow
import cu.osmel.tvplus.ui.componets.CustomFloatingButton
import cu.osmel.tvplus.ui.componets.SnackbarMessage
import cu.osmel.tvplus.ui.componets.list.ListEmpty
import cu.osmel.tvplus.ui.componets.list.TvShowItem
import cu.osmel.tvplus.ui.componets.searchview.SearchView
import cu.osmel.tvplus.ui.view.tvshow.event.TvShowEvent
import cu.osmel.tvplus.ui.viewmodel.tvshow.TvShowViewModel
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
@ExperimentalPagerApi
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalComposeUiApi
@Composable
fun TvShowView(
    darkMode: MutableState<Boolean>,
    viewModel: MainViewModel,
    tvShowViewModel: TvShowViewModel = hiltViewModel(),
    onAddClick: () -> Unit,
    onItemClick: (Long) -> Unit,
) {
    viewModel.setCurrentScreen(AppView.TvShowView)
    viewModel.setCurrentAppBarActions(emptyList())
    val state = tvShowViewModel.state.value

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
                    onAddClick()
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
            TvShowViewContent(
                modifier = Modifier.padding(it),
                onDelete = { tvShow ->
                    println(tvShow)
                    tvShowViewModel.onEvent(
                        TvShowEvent.DeleteTvShow(tvShow)
                    )
                },
                onItemClick = { tvShow -> tvShow.serial.id?.let { it1 -> onItemClick(it1) } },
                listTvShows = state.listTvShows,
                textSearch = tvShowViewModel.textSearchState.value,
                tvShowViewModel = tvShowViewModel
            )
        }
    )
}

@ExperimentalPagerApi
@ExperimentalComposeUiApi
@Composable
fun TvShowViewContent(
    modifier: Modifier = Modifier,
    onDelete: (TvShow) -> Unit,
    onItemClick: (TvShow) -> Unit,
    listTvShows: List<TvShow> = emptyList(),
    textSearch: String,
    tvShowViewModel: TvShowViewModel
) {
    val scrollState = rememberLazyGridState()
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    SnackbarMessage(
        hostState = snackbarHostState,
        onClose = { snackbarHostState.currentSnackbarData?.dismiss() }
    )

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
                    tvShowViewModel.onSearchTextChanged(it)
                },
                onCancel = {
                    tvShowViewModel.onCancelSearch()
                    focusManager.clearFocus()
                }
            )
            if (listTvShows.isEmpty())
                ListEmpty()
            else {
                LazyVerticalGrid(
                    state = scrollState,
                    columns = GridCells.Fixed(2),
                ) {
                    items(listTvShows) { item ->
                        TvShowItem(
                            item,
                            true,
                            onItemClick = { onItemClick(item) },
                            onDelete = {
                                onDelete(item)
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = context.getString(R.string.success_delete),
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}