package cu.osmel.tvplus.ui.componets.dialog

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Created by Osmel Pérez Alzola(osmelpa86@gmail.com) on 22/5/22
 */
@Composable
fun ActionTextSection(
    dismissDialog: () -> Unit?,
    actionLeftText: String?,
    successDialog: () -> Unit,
    actionRightText: String?
) {
    Row(
        horizontalArrangement = Arrangement.End,
        modifier = Modifier.fillMaxWidth()
    ) {
        TextButton(onClick = { dismissDialog() }) {
            Text(text = actionLeftText!!, color = MaterialTheme.colors.primary)
        }

        Spacer(modifier = Modifier.width(4.dp))
        TextButton(onClick = { successDialog() }) {
            Text(text = actionRightText!!, color = MaterialTheme.colors.primary)
        }
    }
}