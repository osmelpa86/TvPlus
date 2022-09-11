package cu.osmel.tvplus.ui.componets

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Created by Osmel Pérez Alzola(osmelpa86@gmail.com) on 7/4/22
 */

@Composable
fun CustomFloatingButton(
    onClick: () -> Unit,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(imageVector = icon, "", tint = Color.White)
    }

}