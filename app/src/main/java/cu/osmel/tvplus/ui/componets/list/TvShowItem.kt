package cu.osmel.tvplus.ui.componets.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import cu.osmel.tvplus.R
import cu.osmel.tvplus.domain.mappers.cover.toSliderItem
import cu.osmel.tvplus.domain.model.TvShow
import cu.osmel.tvplus.ui.componets.dialog.ConfirmDialog
import cu.osmel.tvplus.ui.componets.slider.DotsIndicator
import cu.osmel.tvplus.ui.componets.slider.SliderView

/**
 * Created by Osmel Pérez Alzola(osmelpa86@gmail.com) on 28/8/22
 */
@ExperimentalPagerApi
@Composable
fun TvShowItem(
    item: TvShow,
    listItem: Boolean,
    onItemClick: () -> Unit,
    onDelete: () -> Unit,
) {
    val itemSliderPageState = rememberPagerState()
    val confirmDeleteTvShowDialog = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .height(350.dp)
            .padding(4.dp)
            .background(
                colorResource(id = R.color.itemBrackground),
                shape = RoundedCornerShape(0.dp)
            )
    ) {
        if (item.coverList.isNotEmpty()) {
            SliderView(
                state = itemSliderPageState,
                items = item.coverList.map { it.toSliderItem() },
                onSelectImage = {},
                onDeleteImage = {},
                listItem = listItem
            )
            Spacer(modifier = Modifier.height(4.dp))
            DotsIndicator(
                totalDots = item.coverList.size,
                selectedIndex = itemSliderPageState.currentPage
            )
            Spacer(modifier = Modifier.height(5.dp))
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(301.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier
                        .size(80.dp),
                    imageVector = Icons.Rounded.InsertPhoto,
                    contentDescription = null,
                    tint = Color.White
                )
                Text(
                    text = item.serial.title,
                    modifier = Modifier
                        .align(alignment = Alignment.BottomCenter)
                        .padding(start = 0.dp, top = 0.dp, end = 0.dp, bottom = 5.dp),
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(fontSize = 16.sp),
                    color = colorResource(id = R.color.itemIconColor)
                )
            }
        }
        Divider(modifier = Modifier.fillMaxWidth())
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(0.dp)
            ) {
                IconButton(
                    onClick = { },
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Info,
                        contentDescription = null,
                        tint = colorResource(id = R.color.itemIconColor)
                    )
                }
                IconButton(
                    onClick = { onItemClick() },
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Edit,
                        contentDescription = null,
                        tint = colorResource(id = R.color.itemIconColor)
                    )
                }

                IconButton(
                    onClick = {
                        confirmDeleteTvShowDialog.value = true
                    },
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Delete,
                        contentDescription = null,
                        tint = colorResource(id = R.color.itemIconColor)
                    )
                }
            }
        }
    }

    if (confirmDeleteTvShowDialog.value) {
        ConfirmDialog(
            message = "${stringResource(id = R.string.tv_show_delete_message)} ${item.serial.title}",
            dismissDialog = { confirmDeleteTvShowDialog.value = false },
            successDialog = {
                confirmDeleteTvShowDialog.value = false
                onDelete()
            }
        )
    }
}