package cu.osmel.tvplus.ui.componets.dialog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Circle
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
fun Dialog(
    title: Int? = R.string.no_title,
    leftIcon: ImageVector? = Icons.Rounded.Circle,
    content: @Composable () -> Unit? = {},
    dismissDialog: () -> Unit? = {},
    successDialog: () -> Unit,
    actionRightText: String? = stringResource(id = R.string.accept),
    actionLeftText: String? = stringResource(id = R.string.cancel),
    actionRightIcon: ImageVector? = Icons.Rounded.Check,
    actionLeftIcon: ImageVector? = Icons.Rounded.Cancel,
    enabledActions: Boolean? = true
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
                DialogHeaderSection(leftIcon, title, dismissDialog)
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                )
                content()
                ActionSection(
                    dismissDialog = dismissDialog,
                    actionLeftIcon = actionLeftIcon,
                    actionLeftText = actionLeftText,
                    successDialog = successDialog,
                    actionRightIcon = actionRightIcon,
                    actionRightText = actionRightText,
                    enabledActions = enabledActions
                )
            }
        }
    }
}





