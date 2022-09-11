package cu.osmel.tvplus.ui.componets.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cu.osmel.tvplus.R
import cu.osmel.tvplus.domain.model.Season
import cu.osmel.tvplus.ui.componets.dialog.ConfirmDialog
import cu.osmel.tvplus.ui.componets.util.statusColor

/**
 * Created by Osmel Pérez Alzola(osmelpa86@gmail.com) on 8/4/22
 */
@Composable
fun SeasonItem(
    season: Season,
    onEdit: () -> Unit? = {},
    onDelete: () -> Unit? = {}
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
                    .size(40.dp)
                    .background(
                        color = colorResource(id = statusColor(season.status)),
                        shape = CircleShape
                    )
            ) {
                Text(
                    text = season.number.toString(),
                    color = Color.White,
                    style = LocalTextStyle.current.copy(
                        fontWeight = FontWeight.Bold
                    )

                )
            }
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(10.dp, 0.dp, 10.dp, 0.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = Icons.Rounded.CalendarToday,
                        contentDescription = null,
                        tint = MaterialTheme.colors.secondaryVariant
                    )
                    Text(
                        text = season.year,
                        style = MaterialTheme.typography.body1.copy(
                            color = MaterialTheme.colors.secondaryVariant
                        ),
                        modifier = Modifier.padding(2.dp, 0.dp, 0.dp, 0.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = Icons.Rounded.Snooze,
                        contentDescription = null,
                        tint = colorResource(id = statusColor(season.status)),
                    )
                    Text(
                        text = season.status,
                        style = MaterialTheme.typography.body1.copy(
                            color = colorResource(id = statusColor(season.status))
                        ),
                        modifier = Modifier.padding(2.dp, 0.dp, 0.dp, 0.dp)
                    )

                    Spacer(modifier = Modifier.width(5.dp))
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = Icons.Rounded.PlaylistPlay,
                        contentDescription = null,
                        tint = MaterialTheme.colors.secondaryVariant,
                    )
                    Text(
                        text = season.totalChapters.toString(),
                        style = MaterialTheme.typography.body1.copy(
                            color = MaterialTheme.colors.secondaryVariant
                        ),
                        modifier = Modifier.padding(2.dp, 0.dp, 0.dp, 0.dp)
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(10.dp, 0.dp, 10.dp, 0.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = Icons.Rounded.Movie,
                        contentDescription = null,
                        tint = MaterialTheme.colors.secondaryVariant
                    )
                    Text(
                        text = stringResource(id = R.string.not_assign),
                        style = MaterialTheme.typography.body1.copy(
                            color = MaterialTheme.colors.secondaryVariant
                        ),
                        modifier = Modifier.padding(2.dp, 0.dp, 0.dp, 0.dp)
                    )
                }
            }
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
            message = "${stringResource(id = R.string.delete_message)} ${season.number}",
            dismissDialog = { confirmDeleteGenreDialog.value = false },
            successDialog = {
                confirmDeleteGenreDialog.value = false
                onDelete()
            }
        )
    }
}