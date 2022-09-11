package cu.osmel.tvplus.ui.view.country

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
import cu.osmel.tvplus.domain.model.Country
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
import cu.osmel.tvplus.ui.view.country.event.CountryEvent
import cu.osmel.tvplus.ui.viewmodel.common.MainViewModel
import cu.osmel.tvplus.ui.viewmodel.country.CountryViewModel
import kotlinx.coroutines.launch
import java.io.InputStream
import kotlin.math.roundToInt


/**
 * Created by Osmel Pérez Alzola(osmelpa86@gmail.com) on 29/5/22
 */
@ExperimentalComposeUiApi
@Composable
fun CountryView(
    darkMode: MutableState<Boolean>,
    viewModel: MainViewModel,
    countryViewModel: CountryViewModel = hiltViewModel(),
) {
    viewModel.setCurrentScreen(AppView.CountryView)
    viewModel.setCurrentAppBarActions(emptyList())
    var openAddCountryDialog by remember { mutableStateOf(false) }
    val state = countryViewModel.state.value
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val name: InputWrapper by countryViewModel.name.collectAsState()
    val areInputsValid by countryViewModel.areInputsValid.collectAsState()
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
                    val listCountryType = object : TypeToken<List<Country>>() {}.type
                    val countries: List<Country> = gson.fromJson(input, listCountryType)
                    countryViewModel.onEvent(CountryEvent.InsertAllCountry(countries))
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
        countryViewModel.imageUri = uri
    }

    val uploadMenuitems = listOf(
        MenuItem.Option(
            name = R.string.cancel,
            icon = Icons.Rounded.Clear,
            itemClick = { countryViewModel.cancelSelectImage() }
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
                    openAddCountryDialog = true
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
            CountryViewContent(
                modifier = Modifier.padding(it),
                onDelete = {
                    scope.launch {
                        countryViewModel.onEvent(CountryEvent.DeleteCountry(it))
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = context.getString(R.string.success_delete),
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                },
                onEdit = { country ->
                    countryViewModel.edit = true
                    if (country != null) {
                        countryViewModel.countryModel = country
                    }
                    countryViewModel.onNameEntered(input = country!!.name)
                    countryViewModel.bitmap =
                        if (country.image !== null) decodeImageBase64(country.image) else null
                    openAddCountryDialog = true
                },
                listCountry = state.listCountrys,
                textSearch = countryViewModel.textSearchState,
                countryViewModel = countryViewModel
            )
        }
    )
    ManageCountryView(
        countryViewModel = countryViewModel,
        showDialog = openAddCountryDialog,
        dismissDialog = {
            openAddCountryDialog = false
            countryViewModel.reset()
        },
        successDialog = {
            val image: String? = countryViewModel.image.ifEmpty {
                null
            }

            if (!countryViewModel.edit) {
                countryViewModel.onEvent(
                    CountryEvent.InsertCountry(
                        Country(
                            name = countryViewModel.name.value.value,
                            image = image
                        )
                    )
                )
                openAddCountryDialog = false
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = context.getString(R.string.success_insert),
                        duration = SnackbarDuration.Short
                    )
                }
            } else {
                countryViewModel.onEvent(
                    CountryEvent.UpdateEvent(
                        Country(
                            id = countryViewModel.countryModel.id,
                            name = countryViewModel.name.value.value,
                            image = image
                        )
                    )
                )
                openAddCountryDialog = false
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
        bitmap = countryViewModel.bitmap,
        options = uploadMenuitems
    )

    countryViewModel.imageUri.let {
        val uri = it
        if (uri != null) {
            if (Build.VERSION.SDK_INT < 28) {
                countryViewModel.bitmap =
                    MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
                countryViewModel.image = encodeImageBase64(uri, context)
            } else {
                val source = ImageDecoder
                    .createSource(context.contentResolver, uri)
                countryViewModel.bitmap = ImageDecoder.decodeBitmap(source)
                countryViewModel.image = encodeImageBase64(uri, context)
            }
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun CountryViewContent(
    modifier: Modifier = Modifier,
    onDelete: (country: Country) -> Unit,
    onEdit: (country: Country?) -> Unit,
    listCountry: List<Country> = emptyList(),
    textSearch: String,
    countryViewModel: CountryViewModel,
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
                    countryViewModel.onSearchTextChanged(it)
                },
                onCancel = {
                    countryViewModel.onCancelSearch()
                    focusManager.clearFocus()
                }
            )
            if (listCountry.isEmpty())
                ListEmpty()
            else {
                LazyColumn(
                    state = scrollState
                ) {
                    items(listCountry) { country ->
                        SimpleItemWithLeftImage(
                            text = country.name,
                            type = R.string.the_country,
                            bitmap = country.image,
                            onEdit = { onEdit(country) },
                            onDelete = { onDelete(country) }
                        )
                    }
                }
            }
        }
    }
}