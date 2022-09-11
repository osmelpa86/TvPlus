@file:OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)

package cu.osmel.tvplus.ui.navegation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.google.accompanist.pager.ExperimentalPagerApi

/**
 * Created by Osmel Pérez Alzola(osmelpa86@gmail.com) on 23/8/22
 */
class TvShowTabItems(val icon:ImageVector, val content: @Composable () -> Unit = {})