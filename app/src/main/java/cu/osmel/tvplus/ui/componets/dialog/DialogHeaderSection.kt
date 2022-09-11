package cu.osmel.tvplus.ui.componets.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Created by Osmel Pérez Alzola(osmelpa86@gmail.com) on 11/5/22
 */
@Composable
fun DialogHeaderSection(
    leftIcon: ImageVector?,
    title: Int?,
    dismissDialog: () -> Unit?
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.primary)
    )
    {
        Row(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = leftIcon!!,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.padding(12.dp, 0.dp, 0.dp, 0.dp),
            )
            Text(
                modifier = Modifier.padding(8.dp, 0.dp, 8.dp, 0.dp),
                text = stringResource(id = title!!),
                style = MaterialTheme.typography.h1.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    fontStyle = FontStyle.Italic
                ),
            )
        }
        IconButton(
            onClick = { dismissDialog() },
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Icon(
                imageVector = Icons.Rounded.Cancel,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}
