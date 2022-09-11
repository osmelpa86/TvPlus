package cu.osmel.tvplus.ui.componets.menu

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.Divider

/**
 * Created by Osmel Pérez Alzola(osmelpa86@gmail.com) on 31/5/22
 */
sealed interface MenuItem {
    data class Option(
        val name: Int,
        val icon: ImageVector?,
        val enabled: Boolean = true,
        val divider: Boolean = false,
        val itemClick: () -> Unit
    ) : MenuItem
}
