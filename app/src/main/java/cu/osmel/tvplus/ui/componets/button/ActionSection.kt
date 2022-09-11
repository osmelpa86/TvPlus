package cu.osmel.tvplus.ui.componets.dialog

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import cu.osmel.tvplus.ui.componets.ActionButton
import cu.osmel.tvplus.ui.componets.ActionOutlinedButton

/**
 * Created by Osmel Pérez Alzola(osmelpa86@gmail.com) on 10/5/22
 */
@Composable
fun ActionSection(
    dismissDialog: () -> Unit?,
    actionLeftIcon: ImageVector?,
    actionLeftText: String?,
    successDialog: () -> Unit,
    actionRightIcon: ImageVector?,
    actionRightText: String?,
    enabledActions: Boolean? = true
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.End,

        ) {
        ActionOutlinedButton(
            dismissDialog,
            actionLeftIcon,
            actionLeftText,
        )
        ActionButton(
            successDialog = successDialog,
            actionRightIcon = actionRightIcon,
            actionRightText = actionRightText,
            enabledActions = enabledActions
        )
    }
}