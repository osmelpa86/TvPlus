package cu.osmel.tvplus.ui.componets.topbar

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Created by Osmel Pérez Alzola(osmelpa86@gmail.com) on 7/6/22
 */

data class ActionItem(
    val icon: ImageVector,
    val onClick: () -> Unit,
    val enabled: Boolean = true
)