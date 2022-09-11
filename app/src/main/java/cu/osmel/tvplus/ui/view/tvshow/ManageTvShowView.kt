package cu.osmel.tvplus.ui.view.tvshow

import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.*
import cu.osmel.tvplus.R
import cu.osmel.tvplus.data.database.entities.TvShowEntity
import cu.osmel.tvplus.domain.mappers.general.toCountry
import cu.osmel.tvplus.domain.mappers.general.toCover
import cu.osmel.tvplus.domain.mappers.general.toGenre
import cu.osmel.tvplus.domain.model.TvShow
import cu.osmel.tvplus.ui.componets.SnackbarMessage
import cu.osmel.tvplus.ui.componets.input.*
import cu.osmel.tvplus.ui.componets.input.chip.ChipGroupMultiSelection
import cu.osmel.tvplus.ui.componets.input.chip.ChipItem
import cu.osmel.tvplus.ui.componets.input.exposeddropdownmenu.ExposedDropdownItem
import cu.osmel.tvplus.ui.componets.input.exposeddropdownmenu.ExposedDropdownMenu
import cu.osmel.tvplus.ui.componets.input.exposeddropdownmenu.ExposedImageDropdownMenu
import cu.osmel.tvplus.ui.componets.list.ChannelSelectItem
import cu.osmel.tvplus.ui.componets.list.SeasonSelectItem
import cu.osmel.tvplus.ui.componets.slider.DotsIndicator
import cu.osmel.tvplus.ui.componets.slider.SliderItem
import cu.osmel.tvplus.ui.componets.slider.SliderView
import cu.osmel.tvplus.ui.componets.topbar.ActionItem
import cu.osmel.tvplus.ui.componets.util.decodeImageBase64
import cu.osmel.tvplus.ui.componets.util.encodeImageBase64
import cu.osmel.tvplus.ui.navegation.AppView
import cu.osmel.tvplus.ui.navegation.TvShowTabItems
import cu.osmel.tvplus.ui.view.tvshow.event.TvShowEvent
import cu.osmel.tvplus.ui.viewmodel.common.MainViewModel
import cu.osmel.tvplus.ui.viewmodel.tvshow.TvShowViewModel
import kotlinx.coroutines.launch

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun ManageTvShowView(
    darkMode: MutableState<Boolean>,
    viewModel: MainViewModel,
    tvShowId: Long? = null,
    tvShowViewModel: TvShowViewModel = hiltViewModel(),
    onCancelClick: () -> Unit,
) {
    viewModel.setCurrentScreen(AppView.ManageTvShowView)
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val title: InputWrapper by tvShowViewModel.title.collectAsState()
    val year: InputWrapper by tvShowViewModel.year.collectAsState()
    val country: InputWrapper by tvShowViewModel.country.collectAsState()
    val duration: InputWrapper by tvShowViewModel.duration.collectAsState()
    val type: InputWrapper by tvShowViewModel.type.collectAsState()
    val status: InputWrapper by tvShowViewModel.status.collectAsState()
    val synopsis: InputWrapper by tvShowViewModel.synopsis.collectAsState()
    val sliderPageState = rememberPagerState()
    val areInputsValid by tvShowViewModel.areInputsValid.collectAsState()
    val areInputsValidExtra by tvShowViewModel.areInputsValidExtra.collectAsState()

    if (tvShowId !== null) {
        tvShowViewModel.edit = true
        tvShowViewModel.onEvent(
            TvShowEvent.LoadByIdTvShow(tvShowId)
        )
    }

    viewModel.setCurrentAppBarActions(
        listOf(
            ActionItem(
                icon = Icons.Rounded.Check,
                onClick = {
                    insertOrUpdate(tvShowViewModel = tvShowViewModel, tvShowId = tvShowId)
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = context.getString(R.string.success_insert),
                            duration = SnackbarDuration.Short
                        )
                        onCancelClick()
                    }
                },
                enabled = areInputsValid && areInputsValidExtra
            ),
            ActionItem(icon = Icons.Rounded.Cancel, onClick = {
                tvShowViewModel.reset()
                onCancelClick()
            })
        )
    )

    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            if (Build.VERSION.SDK_INT < 28) {
                tvShowViewModel.selectedFronts.add(
                    SliderItem(
                        image = MediaStore.Images.Media.getBitmap(
                            context.contentResolver,
                            uri
                        ), imageBase64 = encodeImageBase64(uri, context)
                    )
                )
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, uri)
                tvShowViewModel.selectedFronts.add(
                    SliderItem(
                        image = ImageDecoder.decodeBitmap(
                            source
                        ), imageBase64 = encodeImageBase64(uri, context)
                    )
                )
            }
        }
    }

    val tabs = listOf(
        TvShowTabItems(icon = Icons.Rounded.NoteAdd, content = {
            GeneralData(
                tvShowViewModel = tvShowViewModel,
                title = title,
                year = year,
                country = country,
                duration = duration,
                type = type,
                status = status,
                synopsis = synopsis
            )
        }
        ),
        TvShowTabItems(
            icon = Icons.Rounded.CastConnected,
            content = { Channel(tvShowViewModel = tvShowViewModel) }),
        TvShowTabItems(icon = Icons.Rounded.AddPhotoAlternate, content = {
            Cover(
                tvShowViewModel = tvShowViewModel, launcher = launcher,
                sliderPageState = sliderPageState
            )
        }),
        TvShowTabItems(
            icon = Icons.Rounded.Event,
            content = { Season(tvShowViewModel = tvShowViewModel) })
    )
    val pagerState = rememberPagerState()

    Scaffold { padding ->
        Column(modifier = Modifier.padding(paddingValues = padding)) {
            Tabs(tabs = tabs, pagerState = pagerState)
            TabsContent(tabs = tabs, pagerState = pagerState)
        }
    }

    SnackbarMessage(
        hostState = snackbarHostState,
        onClose = { snackbarHostState.currentSnackbarData?.dismiss() }
    )
}

@ExperimentalPagerApi
@Composable
fun Tabs(tabs: List<TvShowTabItems>, pagerState: PagerState) {
    val scope = rememberCoroutineScope()
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        contentColor = Color.White,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
            )
        }) {
        tabs.forEachIndexed { index, tab ->
            LeadingIconTab(
                icon = { Icon(imageVector = tab.icon, contentDescription = "") },
                text = {},
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
            )
        }
    }
}

@ExperimentalPagerApi
@Composable
fun TabsContent(tabs: List<TvShowTabItems>, pagerState: PagerState) {
    HorizontalPager(state = pagerState, count = tabs.size) { page ->
        tabs[page].content()
    }
}

@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun GeneralData(
    tvShowViewModel: TvShowViewModel,
    title: InputWrapper,
    year: InputWrapper,
    country: InputWrapper,
    duration: InputWrapper,
    type: InputWrapper,
    status: InputWrapper,
    synopsis: InputWrapper,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            TextFieldInput(
                modifier = Modifier.fillMaxWidth(),
                labelResId = R.string.title,
                placeholderResId = R.string.title,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                inputWrapper = title,
                onValueChange = tvShowViewModel::onTitleEntered
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    TextFieldNumberInput(
                        labelResId = R.string.year,
                        placeholderResId = R.string.year,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        inputWrapper = year,
                        onValueChange = tvShowViewModel::onYearEntered,
                        regex = Regex("[0-9]*"),
                        totalDigits = 4
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    TextFieldNumberInput(
                        labelResId = R.string.duration,
                        placeholderResId = R.string.duration_placeholder,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        inputWrapper = duration,
                        onValueChange = tvShowViewModel::onDurationEntered,
                        regex = Regex("[0-9]*"),
                        visualTransformation = MaskVisualTransformation("##:##"),
                        totalDigits = 4
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            ExposedImageDropdownMenu(
                modifier = Modifier.fillMaxWidth(),
                inputWrapper = country,
                labelResId = R.string.country,
                placeholderResId = R.string.country,
                options = tvShowViewModel.stateCountry.value.listCountrys.map {
                    ExposedDropdownItem(
                        id = it.id,
                        text = it.name,
                        image = if (it.image !== null) decodeImageBase64(it.image) else null
                    )
                },
                selected = tvShowViewModel.countrySelected,
                onChange = {
                    tvShowViewModel.countrySelected = it
                    tvShowViewModel.onCountryEntered(it.text!!)
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    ExposedDropdownMenu(
                        inputWrapper = type,
                        labelResId = R.string.type,
                        placeholderResId = R.string.type,
                        options = tvShowViewModel.listTypes,
                        selected = tvShowViewModel.typeSelected,
                        onChange = {
                            tvShowViewModel.typeSelected = it
                            tvShowViewModel.onTypeEntered(it.text!!)
                        }
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    ExposedDropdownMenu(
                        inputWrapper = status,
                        labelResId = R.string.status,
                        placeholderResId = R.string.status,
                        options = tvShowViewModel.listStatus,
                        selected = tvShowViewModel.statusSelected,
                        onChange = {
                            tvShowViewModel.statusSelected = it
                            tvShowViewModel.onStatusEntered(it.text!!)
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            TextFieldInput(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                labelResId = R.string.synopsis,
                placeholderResId = R.string.synopsis,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                inputWrapper = synopsis,
                onValueChange = tvShowViewModel::onSynopsisEntered,
                maxLines = 5
            )
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colors.onSurface.copy(alpha = TextFieldDefaults.BackgroundOpacity),
                        shape = RoundedCornerShape(
                            topStart = 4.dp,
                            topEnd = 4.dp,
                            bottomEnd = 0.dp,
                            bottomStart = 0.dp
                        )
                    )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.genres),
                        modifier = Modifier.align(Alignment.Start)
                    )
                    ChipGroupMultiSelection(
                        listChipItems = tvShowViewModel.stateGenre.value.listGenres.map {
                            ChipItem(
                                id = it.id,
                                text = it.name
                            )
                        },
                        selectedItems = tvShowViewModel.selectedGenres,
                        onSelectedChanged = { changedSelection ->
                            val oldList: MutableList<ChipItem> =
                                tvShowViewModel.selectedGenres.toMutableList()

                            if (oldList.contains(changedSelection)) {
                                oldList.remove(changedSelection)
                            } else {
                                oldList.add(changedSelection)
                            }

                            tvShowViewModel.selectedGenres = oldList
                        }
                    )
                }
                Divider(
                    modifier = Modifier
                        .align(Alignment.BottomStart),
                    color = Color.Gray
                )
            }
        }
    }
}

@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun Season(tvShowViewModel: TvShowViewModel) {
    Surface(
        color = MaterialTheme.colors.surface,
        modifier = Modifier
    )
    {
        Spacer(modifier = Modifier.height(5.dp))
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(tvShowViewModel.stateSeason.value.listSeasons) { item ->
                SeasonSelectItem(
                    item,
                    onSelectItem = {
                        tvShowViewModel.updateSelectedSeason(
                            item,
                            item in tvShowViewModel.selectedSeasons,
                        )
                    },
                    tvShowViewModel.selectedSeasons,
                )
            }
        }
    }
}

@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun Channel(tvShowViewModel: TvShowViewModel) {
    Surface(
        color = MaterialTheme.colors.surface,
        modifier = Modifier
    )
    {
        Spacer(modifier = Modifier.height(5.dp))
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(tvShowViewModel.stateChannel.value.listChannels) { item ->
                ChannelSelectItem(
                    item,
                    onSelectItem = {
                        tvShowViewModel.updateSelectedChannel(
                            item,
                            item in tvShowViewModel.selectedChannel,
                        )
                    },
                    tvShowViewModel.selectedChannel,
                )
            }
        }
    }
}

@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun Cover(
    tvShowViewModel: TvShowViewModel, launcher: ManagedActivityResultLauncher<String, Uri?>,
    sliderPageState: PagerState,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(8.dp))
        SliderView(
            state = sliderPageState,
            items = tvShowViewModel.selectedFronts,
            onSelectImage = {
                launcher.launch("image/*")
            },
            onDeleteImage = {
                tvShowViewModel.selectedFronts.remove(it)
            }
        )
        Spacer(modifier = Modifier.height(4.dp))
        DotsIndicator(
            totalDots = tvShowViewModel.selectedFronts.size,
            selectedIndex = sliderPageState.currentPage
        )
        Spacer(modifier = Modifier.height(12.dp))
    }
}


private fun insertOrUpdate(tvShowViewModel: TvShowViewModel, tvShowId: Long?) {
    if (!tvShowViewModel.edit) {
        tvShowViewModel.onEvent(
            TvShowEvent.InsertTvShow(
                TvShow(
                    serial = TvShowEntity(
                        title = tvShowViewModel.title.value.value,
                        year = tvShowViewModel.year.value.value.toInt(),
                        idCountry = tvShowViewModel.countrySelected.id!!,
                        duration = tvShowViewModel.duration.value.value/*timeFilter(
                        AnnotatedString(text = tvShowViewModel.duration.value.value),
                        "xx:xx"
                    ).text.text*/,
                        type = tvShowViewModel.type.value.value,
                        synopsis = tvShowViewModel.synopsis.value.value,
                        status = tvShowViewModel.status.value.value
                    ),
                    country = tvShowViewModel.countrySelected.toCountry(),
                    genreList = tvShowViewModel.selectedGenres.map { it.toGenre() },
                    coverList = tvShowViewModel.selectedFronts.map { it.toCover() },
                    channelList = tvShowViewModel.selectedChannel,
                    seasonList = tvShowViewModel.selectedSeasons
                )
            )
        )
    } else {
        tvShowViewModel.onEvent(
            TvShowEvent.UpdateTvShow(
                TvShow(
                    serial = TvShowEntity(
                        id = tvShowId,
                        title = tvShowViewModel.title.value.value,
                        year = tvShowViewModel.year.value.value.toInt(),
                        idCountry = tvShowViewModel.countrySelected.id!!,
                        duration = tvShowViewModel.duration.value.value,
                        type = tvShowViewModel.type.value.value,
                        synopsis = tvShowViewModel.synopsis.value.value,
                        status = tvShowViewModel.status.value.value
                    ),
                    country = tvShowViewModel.countrySelected.toCountry(),
                    genreList = tvShowViewModel.selectedGenres.map { it.toGenre() },
                    coverList = tvShowViewModel.selectedFronts.map { it.toCover() },
                    channelList = tvShowViewModel.selectedChannel,
                    seasonList = tvShowViewModel.selectedSeasons
                )
            )
        )
    }
}