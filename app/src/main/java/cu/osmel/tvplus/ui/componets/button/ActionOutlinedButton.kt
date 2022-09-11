package cu.osmel.tvplus.ui.componets

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

/**
 * Created by Osmel Pérez Alzola(osmelpa86@gmail.com) on 10/5/22
 */
@Composable
fun ActionOutlinedButton(
    dismissDialog: () -> Unit?,
    actionLeftIcon: ImageVector?,
    actionLeftText: String?,
    enabledActions: Boolean? = true
) {
    OutlinedButton(
        onClick = { dismissDialog() },
        modifier = Modifier
            .padding(0.dp, 0.dp, 8.dp, 0.dp),
        enabled = enabledActions!!
    ) {
        Icon(
            imageVector = actionLeftIcon!!,
            contentDescription = null,
            tint = MaterialTheme.colors.primary,
            modifier = Modifier.padding(0.dp, 0.dp, 8.dp, 0.dp),
        )
        Text(
            text = actionLeftText!!,
            style = MaterialTheme.typography.button.copy(
                color = MaterialTheme.colors.primary
            ),
        )
    }
}