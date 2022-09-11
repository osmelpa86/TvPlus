package cu.osmel.tvplus.ui.componets.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CircleNotifications
import androidx.compose.material.icons.rounded.ViewList
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cu.osmel.tvplus.R

/**
 * Created by Osmel Pérez Alzola(osmelpa86@gmail.com) on 18/5/22
 */
@Preview(showBackground = true)
@Composable
fun ListEmpty(
    message: String? = stringResource(id = R.string.data_empty_message),
    imageVector: ImageVector? = Icons.Rounded.ViewList
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                modifier = Modifier.size(150.dp),
                imageVector = Icons.Rounded.CircleNotifications,
                contentDescription = null,
                tint = MaterialTheme.colors.primary
            )
            Box(
                modifier = Modifier
                    .padding(15.dp)
                    .align(Alignment.BottomEnd)
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .background(
                            color = MaterialTheme.colors.secondary,
                            shape = CircleShape
                        )
                        .shadow(
                            elevation = 38.dp,
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        modifier = Modifier
                            .size(32.dp)
                            .align(Alignment.Center),
                        imageVector = imageVector!!,
                        contentDescription = null,
                        tint = Color.White,
                    )
                }
            }
        }
        Text(
            text = message.toString(),
            style = MaterialTheme.typography.h5.copy(
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colors.primary,
                fontStyle = FontStyle.Italic,
                shadow = Shadow(
                    color = MaterialTheme.colors.primary,
                    offset = Offset(1f, 1f),
                    blurRadius = 2f
                )
            ),
            textAlign = TextAlign.Center
        )
    }
}