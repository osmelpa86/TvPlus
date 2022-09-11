package cu.osmel.tvplus.ui.componets.dialog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import cu.osmel.tvplus.R

/**
 * Created by Osmel Pérez Alzola(osmelpa86@gmail.com) on 6/5/22
 */
@Composable
fun ConfirmDialog(
    message: String,
    icon: ImageVector? = Icons.Rounded.Delete,
    dismissDialog: () -> Unit = {},
    successDialog: () -> Unit,
    actionRightText: String? = stringResource(id = R.string.accept),
    actionLeftText: String? = stringResource(id = R.string.cancel),
) {
    Dialog(
        onDismissRequest = { dismissDialog() },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        ),
    ) {
        Card(
            elevation = 16.dp,
            shape = RoundedCornerShape(8.dp)
        ) {
            Column {
                ConfirmDialogHeader(icon)
                Column(modifier = Modifier.padding(16.dp)) {
                    ConfirmDialogContent(message)
                    Spacer(modifier = Modifier.height(16.dp))
                    ActionTextSection(
                        dismissDialog,
                        actionLeftText,
                        successDialog,
                        actionRightText
                    )
                }
            }
        }
    }
}




