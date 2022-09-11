package cu.osmel.tvplus.ui.componets.slider

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import cu.osmel.tvplus.R
import cu.osmel.tvplus.ui.componets.list.ListEmpty

/**
 * Created by Osmel Pérez Alzola(osmelpa86@gmail.com) on 9/6/22
 */
@OptIn(ExperimentalPagerApi::class)
@Composable
fun SliderView(
    state: PagerState,
    items: List<SliderItem>,
    onSelectImage: () -> Unit,
    onDeleteImage: (SliderItem) -> Unit,
    listItem: Boolean = false
) {
    Column(
        modifier =
        if (listItem)
            Modifier.padding(0.dp, 0.dp, 0.dp, 2.dp)
        else
            Modifier.padding(16.dp, 0.dp, 16.dp, 16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            if (!listItem) {
                IconButton(
                    onClick = { onSelectImage() },
                    modifier = Modifier
                        .size(32.dp)
                        .padding(0.dp, 2.dp, 0.dp, 0.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.AddPhotoAlternate,
                        contentDescription = null,
                        tint = MaterialTheme.colors.secondaryVariant
                    )
                }
                if (items.isNotEmpty()) {
                    IconButton(
                        onClick = {
                            onDeleteImage(items[state.currentPage])
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
        }

        if (items.isEmpty()) {
            ListEmpty(message = stringResource(id = R.string.cover_list_empty))
        } else {
            HorizontalPager(
                state = state,
                count = items.size,
            ) { page ->
                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (!listItem) {
                        Image(
                            bitmap = items[page].image!!.asImageBitmap(),
                            contentDescription = "",
                            Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .width(300.dp)
                                .height(500.dp),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Image(
                            bitmap = items[page].image!!.asImageBitmap(),
                            contentDescription = "",
                            Modifier
                                .fillMaxWidth()
                                .height(280.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
    }
}