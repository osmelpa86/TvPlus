package cu.osmel.tvplus.ui.componets.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cu.osmel.tvplus.domain.model.Channel
import cu.osmel.tvplus.domain.model.Season
import cu.osmel.tvplus.ui.componets.util.decodeImageBase64
import cu.osmel.tvplus.ui.componets.util.statusColor

/**
 * Created by Osmel Pérez Alzola(osmelpa86@gmail.com) on 8/4/22
 */
@Composable
fun ChannelSelectItem(
    channel: Channel,
    onSelectItem: () -> Unit? = {},
    selectedChannel: MutableList<Channel>
) {
    Row(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .clickable { onSelectItem() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp)
        ) {
            AnimatedVisibility(
                visible = channel in selectedChannel,
                enter = expandIn(),
                exit = shrinkOut()
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            color = MaterialTheme.colors.primary,
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Check,
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            }
            AnimatedVisibility(
                visible = channel !in selectedChannel,
                enter = expandIn(),
                exit = shrinkOut()
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            color = MaterialTheme.colors.primary,
                            shape = CircleShape
                        )
                ) {
                    if (channel.image !== null && channel.image.isNotEmpty()) {
                        Image(
                            bitmap = decodeImageBase64(channel.image).asImageBitmap(),
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
            }
            Column {

                    Text(
                        text = channel.name,
                        style = MaterialTheme.typography.body1.copy(
                            color = MaterialTheme.colors.secondaryVariant
                        ),
                        modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp)
                    )
            }
        }
    }
    Divider(
        Modifier
            .height(1.dp)
            .fillMaxWidth()
    )
}