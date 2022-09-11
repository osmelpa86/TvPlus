package cu.osmel.tvplus.ui.componets.input.chip

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

/**
 * Created by Osmel Pérez Alzola(osmelpa86@gmail.com) on 9/6/22
 */
@Preview(showBackground = true)
@Composable
fun ChipGroupMultiSelection(
    modifier: Modifier = Modifier,
    listChipItems: List<ChipItem> = emptyList(),
    selectedItems: List<ChipItem?> = emptyList(),
    onSelectedChanged: (ChipItem) -> Unit = {},
) {
    LazyRow {
        items(listChipItems) { item ->
            Chip(
                chipItem = item,
                isSelected = selectedItems.contains(item),
                onSelectionChanged = {
                    onSelectedChanged(it)
                },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChipGroupSingleSelection(
    modifier: Modifier = Modifier,
    listChipItems: List<ChipItem> = listOf(),
    selectedItem: ChipItem? = null,
    onSelectedChanged: (ChipItem) -> Unit = {},
) {
    Column(modifier = modifier) {
        LazyRow {
            items(listChipItems) { item ->
                Chip(
                    chipItem = item,
                    isSelected = selectedItem == item,
                    onSelectionChanged = {
                        onSelectedChanged(it)
                    },
                )
            }
        }
    }
}