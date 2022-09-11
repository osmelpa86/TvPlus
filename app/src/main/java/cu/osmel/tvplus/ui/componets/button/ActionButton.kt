package cu.osmel.tvplus.ui.componets

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

/**
 * Created by Osmel Pérez Alzola(osmelpa86@gmail.com) on 10/5/22
 */
@Composable
fun ActionButton(
    successDialog: () -> Unit,
    actionRightIcon: ImageVector?,
    actionRightText: String?,
    enabledActions: Boolean? = true
) {
    Button(
        onClick = { successDialog() },
        enabled = enabledActions!!
    ) {
        Icon(
            imageVector = actionRightIcon!!,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.padding(0.dp, 0.dp, 8.dp, 0.dp),
        )
        Text(
            text = actionRightText!!,
            style = MaterialTheme.typography.button.copy(
                color = Color.White
            ),
        )
    }
}