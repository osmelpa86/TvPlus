package cu.osmel.tvplus.ui.view.country

import android.graphics.Bitmap
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CastConnected
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
import cu.osmel.tvplus.ui.componets.menu.MenuItem.*
import cu.osmel.tvplus.ui.componets.upload.UploadFile
import cu.osmel.tvplus.ui.viewmodel.country.CountryViewModel

/**
 * Created by Osmel Pérez Alzola(osmelpa86@gmail.com) on 9/4/22
 */
@Composable
fun ManageCountryView(
    countryViewModel: CountryViewModel,
    showDialog: Boolean,
    dismissDialog: () -> Unit,
    successDialog: () -> Unit,
    snackbarHostState: SnackbarHostState,
    areInputsValid: Boolean,
    name: InputWrapper,
    onSelect: () -> Unit,
    bitmap: Bitmap? = null,
    options: List<Option>
) {
    SnackbarMessage(
        hostState = snackbarHostState,
        onClose = { snackbarHostState.currentSnackbarData?.dismiss() }
    )

    if (showDialog) {
        Dialog(
            title = R.string.manage_country,
            leftIcon = Icons.Rounded.CastConnected,
            content = {
                ManageCountryView(
                    countryViewModel = countryViewModel,
                    name = name,
                    onSelect = onSelect,
                    bitmap = bitmap,
                    options = options
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

@Composable
fun ManageCountryView(
    countryViewModel: CountryViewModel,
    name: InputWrapper,
    onSelect: () -> Unit,
    bitmap: Bitmap? = null,
    options: List<Option>,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextFieldInput(
            modifier = Modifier.fillMaxWidth(),
            labelResId = R.string.name,
            placeholderResId = R.string.name,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            inputWrapper = name,
            onValueChange = countryViewModel::onNameEntered,
            regex = Regex("[a-zA-z\\s]*")
        )
        Spacer(modifier = Modifier.height(16.dp))
        UploadFile(onSelect = onSelect, bitmap = bitmap, options = options)
    }
}