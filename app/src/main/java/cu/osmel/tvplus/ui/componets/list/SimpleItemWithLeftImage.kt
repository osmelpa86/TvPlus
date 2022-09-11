package cu.osmel.tvplus.ui.componets.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.InsertPhoto
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cu.osmel.tvplus.R
import cu.osmel.tvplus.ui.componets.dialog.ConfirmDialog
import cu.osmel.tvplus.ui.componets.util.decodeImageBase64

/**
 * Created by Osmel Pérez Alzola(osmelpa86@gmail.com) on 8/4/22
 */
@Composable
fun SimpleItemWithLeftImage(
    text: String,
    bitmap: String? = null,
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
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(50.dp)
                    .background(
                        color = MaterialTheme.colors.primary,
                        shape = CircleShape
                    )
            ) {
                if (bitmap !== null && bitmap.isNotEmpty()) {
                    Image(
                        bitmap = decodeImageBase64(bitmap).asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier
                            .size(150.dp)
                            .clip(RoundedCornerShape(300.dp)),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = Icons.Rounded.InsertPhoto,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
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