package cu.osmel.tvplus.ui.view.season

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Event
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cu.osmel.tvplus.R
import cu.osmel.tvplus.ui.componets.SnackbarMessage
import cu.osmel.tvplus.ui.componets.dialog.Dialog
import cu.osmel.tvplus.ui.componets.input.InputWrapper
import cu.osmel.tvplus.ui.componets.input.TextFieldInput
import cu.osmel.tvplus.ui.componets.input.TextFieldNumberInput
import cu.osmel.tvplus.ui.componets.input.exposeddropdownmenu.ExposedDropdownMenu
import cu.osmel.tvplus.ui.viewmodel.season.SeasonViewModel

/**
 * Created by Osmel Pérez Alzola(osmelpa86@gmail.com) on 9/4/22
 */
@ExperimentalMaterialApi
@Composable
fun ManageSeasonView(
    seasonViewModel: SeasonViewModel,
    showDialog: Boolean,
    dismissDialog: () -> Unit,
    successDialog: () -> Unit,
    snackbarHostState: SnackbarHostState,
    areInputsValid: Boolean,
    idTvShow: InputWrapper,
    number: InputWrapper,
    totalChapters: InputWrapper,
    status: InputWrapper,
    year: InputWrapper
) {
    SnackbarMessage(
        hostState = snackbarHostState,
        onClose = { snackbarHostState.currentSnackbarData?.dismiss() }
    )

    if (showDialog) {
        Dialog(
            title = R.string.manage_season,
            leftIcon = Icons.Rounded.Event,
            content = {
                ManageSeasonContentView(
                    seasonViewModel = seasonViewModel,
                    idTvShow = idTvShow,
                    number = number,
                    totalChapters = totalChapters,
                    status = status,
                    year = year
                )
            },
            enabledActions = areInputsValid,
            dismissDialog = {
                dismissDialog()
            },
            successDialog = {
                successDialog()
            }
        )
    }
}

@ExperimentalMaterialApi
@Composable
fun ManageSeasonContentView(
    seasonViewModel: SeasonViewModel,
    idTvShow: InputWrapper,
    number: InputWrapper,
    totalChapters: InputWrapper,
    status: InputWrapper,
    year: InputWrapper,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                TextFieldNumberInput(
                    modifier = Modifier.fillMaxWidth(),
                    labelResId = R.string.number,
                    placeholderResId = R.string.number,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    inputWrapper = number,
                    onValueChange = seasonViewModel::onNumberEntered,
                    regex = Regex("[0-9]*")
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
                TextFieldNumberInput(
                    modifier = Modifier.fillMaxWidth(),
                    labelResId = R.string.total_chapter,
                    placeholderResId = R.string.total_chapter,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    inputWrapper = totalChapters,
                    onValueChange = seasonViewModel::onTotalChaptersEntered,
                    regex = Regex("[0-9]*")
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(2f)) {
                ExposedDropdownMenu(
                    inputWrapper = status,
                    labelResId = R.string.status,
                    placeholderResId = R.string.status,
                    options = seasonViewModel.listStatus,
                    selected = seasonViewModel.statusSelected,
                    onChange = {
                        seasonViewModel.statusSelected = it
                        seasonViewModel.onStatusEntered(it.text!!)
                    }
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
                TextFieldNumberInput(
                    labelResId = R.string.year,
                    placeholderResId = R.string.year,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    inputWrapper = year,
                    onValueChange = seasonViewModel::onYearEntered,
                    regex = Regex("[0-9]*"),
                    totalDigits = 4
                )
            }
        }
    }
}