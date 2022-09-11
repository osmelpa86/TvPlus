package cu.osmel.tvplus.ui.componets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Created by Osmel Pérez Alzola(osmelpa86@gmail.com) on 20/5/22
 */

@Composable
fun SnackbarMessage(
    hostState: SnackbarHostState,
    onClose: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        SnackbarHost(
            hostState = hostState,
            snackbar = {
                SnackContent(
                    snackbarData = it,
                    onClose = onClose
                )
            },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
fun SnackContent(
    snackbarData: SnackbarData,
    onClose: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(2.dp, MaterialTheme.colors.primary),
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = Color.White,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = snackbarData.message,
                    style = MaterialTheme.typography.body1
                )
            }
            Row(
                horizontalArrangement = Arrangement.End,
            ) {
                IconButton(
                    onClick = {
                        onClose()
                    },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Cancel,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        }
    }
}