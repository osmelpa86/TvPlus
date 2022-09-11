package cu.osmel.tvplus.ui.view.genre

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.TheaterComedy
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
import cu.osmel.tvplus.ui.viewmodel.genre.GenreViewModel

/**
 * Created by Osmel Pérez Alzola(osmelpa86@gmail.com) on 9/4/22
 */
@Composable
fun ManageGenreView(
    genreViewModel: GenreViewModel,
    showDialog: Boolean,
    dismissDialog: () -> Unit,
    successDialog: () -> Unit,
    snackbarHostState: SnackbarHostState,
    areInputsValid: Boolean,
    name: InputWrapper
) {
    SnackbarMessage(
        hostState = snackbarHostState,
        onClose = { snackbarHostState.currentSnackbarData?.dismiss() }
    )

    if (showDialog) {
        Dialog(
            title = R.string.manage_genre,
            leftIcon = Icons.Rounded.TheaterComedy,
            content = { ManageGenreView(genreViewModel = genreViewModel, name = name) },
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
fun ManageGenreView(genreViewModel: GenreViewModel, name: InputWrapper) {
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
            onValueChange = genreViewModel::onNameEntered,
            regex = Regex("[a-zA-z\\s]*")
        )
    }
}