package cu.osmel.tvplus.ui.componets.dialog

import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.text.style.TextAlign

/**
 * Created by Osmel Pérez Alzola(osmelpa86@gmail.com) on 22/5/22
 */
@Composable
fun ConfirmDialogContent(message: String) {
    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
        Text(
            text = message, style = MaterialTheme.typography.body1.copy(
                textAlign = TextAlign.Center
            )
        )
    }
}