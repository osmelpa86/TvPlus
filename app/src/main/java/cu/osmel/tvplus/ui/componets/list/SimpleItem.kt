package cu.osmel.tvplus.ui.componets.list

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cu.osmel.tvplus.R
import cu.osmel.tvplus.ui.componets.dialog.ConfirmDialog

/**
 * Created by Osmel Pérez Alzola(osmelpa86@gmail.com) on 8/4/22
 */
@Composable
fun SimpleItem(
    text: String,
    onEdit: () -> Unit? = {},
    onDelete: () -> Unit? = {},
    type: Int,
) {
    val confirmDeleteGenreDialog = remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = text,
                style = MaterialTheme.typography.body1.copy(
                    color = MaterialTheme.colors.secondaryVariant
                ),
                modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp)
            )
        }
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.padding(0.dp, 0.dp, 10.dp, 0.dp)
        ) {
            IconButton(
                onClick = { onEdit() },
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Edit,
                    contentDescription = null,
                    tint = MaterialTheme.colors.secondaryVariant
                )
            }
            IconButton(
                onClick = {
                    confirmDeleteGenreDialog.value = true
                },
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Delete,
                    contentDescription = null,
                    tint = MaterialTheme.colors.secondaryVariant
                )
            }
        }
    }
    Divider(
        Modifier
            .height(1.dp)
            .fillMaxWidth()
    )

    if (confirmDeleteGenreDialog.value) {
        ConfirmDialog(
            message = "${stringResource(id = R.string.delete_message)} ${stringResource(id = type)} $text",
            dismissDialog = { confirmDeleteGenreDialog.value = false },
            successDialog = {
                confirmDeleteGenreDialog.value = false
                onDelete()
            }
        )
    }
}