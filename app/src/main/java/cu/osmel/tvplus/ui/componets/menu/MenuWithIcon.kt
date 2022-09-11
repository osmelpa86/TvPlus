package cu.osmel.tvplus.ui.componets.menu

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import cu.osmel.tvplus.ui.componets.menu.MenuItem.Option

/**
 * Created by Osmel Pérez Alzola(osmelpa86@gmail.com) on 31/5/22
 */
@Composable
fun MenuWithIcon(
    expanded: Boolean,
    onDismiss: () -> Unit,
    options: List<Option>
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismiss,
        offset = DpOffset((-20).dp, 0.dp),
        modifier = Modifier.width(162.dp)
    ) {
        options.forEach { option ->
            DropdownMenuItem(
                enabled = option.enabled,
                onClick = {
                    option.itemClick()
                    onDismiss()
                }
            ) {
                option.icon?.let {
                    val values = LocalContentAlpha provides
                            if (option.enabled)
                                ContentAlpha.medium
                            else ContentAlpha.disabled
                    CompositionLocalProvider(values) {
                        Icon(it, contentDescription = null, modifier = Modifier.size(24.dp))
                    }
                }

                Spacer(Modifier.width(10.dp))

                Text(
                    text = stringResource(id = option.name),
                    style = MaterialTheme.typography.body1
                )
            }

            if (option.divider) Divider()
        }
    }
}