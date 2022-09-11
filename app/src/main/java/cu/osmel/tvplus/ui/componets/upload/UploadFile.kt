package cu.osmel.tvplus.ui.componets.upload

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cu.osmel.tvplus.ui.componets.menu.MenuItem.Option
import cu.osmel.tvplus.ui.componets.menu.MenuWithIcon

/**
 * Created by Osmel Pérez Alzola(osmelpa86@gmail.com) on 29/5/22
 */
@Composable
fun UploadFile(
    onSelect: () -> Unit,
    bitmap: Bitmap? = null,
    options: List<Option>
) {
    var imageMenuOpen by remember { mutableStateOf(false) }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(125.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(150.dp)
                .background(
                    color = MaterialTheme.colors.primary,
                    shape = CircleShape
                )
        ) {
            if (bitmap !== null) {
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(150.dp)
                        .clip(RoundedCornerShape(100)),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    modifier = Modifier.size(80.dp),
                    imageVector = Icons.Rounded.InsertPhoto,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(
                        color = MaterialTheme.colors.secondary,
                        shape = CircleShape
                    )
                    .shadow(
                        elevation = 36.dp,
                        shape = CircleShape
                    )
            ) {
                IconButton(onClick = {
                    if (bitmap === null) onSelect() else {
                        imageMenuOpen = true
                    }
                }) {
                    Icon(
                        imageVector = if (bitmap === null) Icons.Rounded.AddPhotoAlternate else Icons.Rounded.Menu,
                        contentDescription = null,
                        tint = Color.White,
                    )
                }

                MenuWithIcon(
                    expanded = imageMenuOpen,
                    onDismiss = {
                        imageMenuOpen = false
                    },
                    options = options
                )
            }
        }
    }

}