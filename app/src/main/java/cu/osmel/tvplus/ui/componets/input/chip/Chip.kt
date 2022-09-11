package cu.osmel.tvplus.ui.componets.input.chip

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Created by Osmel Pérez Alzola(osmelpa86@gmail.com) on 9/6/22
 */
@Composable
fun Chip(
    modifier: Modifier = Modifier,
    onSelectionChanged: (ChipItem) -> Unit = {},
    chipItem: ChipItem,
    isSelected: Boolean = true,
) {
    Surface(
        modifier = modifier.padding(4.dp),
        shape = MaterialTheme.shapes.medium,
        color = if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface.copy(
            alpha = TextFieldDefaults.BackgroundOpacity)
    ) {
        Row(
            modifier = Modifier
                .toggleable(
                    value = isSelected,
                    onValueChange = {
                        onSelectionChanged(chipItem)
                    }
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = chipItem.text!!,
                color = if (isSelected) Color.White else MaterialTheme.colors.onSurface.copy(alpha = TextFieldDefaults.UnfocusedIndicatorLineOpacity),
                modifier = Modifier.padding(8.dp)
            )

            RadioButton(
                selected = isSelected,
                onClick = { onSelectionChanged(chipItem) },
                colors = RadioButtonDefaults.colors(selectedColor = Color.White,
                    unselectedColor = MaterialTheme.colors.onSurface.copy(alpha = TextFieldDefaults.UnfocusedIndicatorLineOpacity))
            )
        }
    }
}