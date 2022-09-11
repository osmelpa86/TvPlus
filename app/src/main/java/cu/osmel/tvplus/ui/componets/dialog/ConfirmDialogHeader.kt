package cu.osmel.tvplus.ui.componets.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

/**
 * Created by Osmel Pérez Alzola(osmelpa86@gmail.com) on 22/5/22
 */
@Composable
fun ConfirmDialogHeader(icon: ImageVector?) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(MaterialTheme.colors.primary),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(
                    color = MaterialTheme.colors.secondary.copy(alpha = 0.2F),
                    shape = CircleShape
                )
                .shadow(
                    elevation = 80.dp,
                    shape = CircleShape
                )
        ) {
            Icon(
                modifier = Modifier
                    .size(60.dp)
                    .align(Alignment.Center),
                imageVector = icon!!,
                contentDescription = null,
                tint = Color.White,
            )
        }
    }
}