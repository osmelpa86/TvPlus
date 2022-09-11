package cu.osmel.tvplus.ui.componets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import cu.osmel.tvplus.R
import cu.osmel.tvplus.ui.navegation.AppView
import cu.osmel.tvplus.ui.navegation.currentRoute
import cu.osmel.tvplus.ui.theme.PrimaryVariant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Created by Osmel Pérez Alzola(osmelpa86@gmail.com) on 28/3/22
 */
@Composable
fun Drawer(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    navHostController: NavHostController,
    items: List<AppView>
) {
    Column {
        Image(
            painter = painterResource(id = R.drawable.side_nav_bar_bg),
            contentDescription = "",
            modifier = Modifier
                .height(160.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
        )
        val currentRoute = currentRoute(navController = navHostController)
        items.forEach { item ->
            DrawerItem(
                item = item,
                selected = currentRoute == item.route,
            ) {
                navHostController.navigate(item.route) {
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items
                    navHostController.graph.startDestinationRoute?.let { route ->
                        popUpTo(route) {
                            saveState = false
                        }
                    }
                    // Avoid multiple copies of the same destination when
                    // reselecting the same item
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }
                // Close drawer
                scope.launch {
                    scaffoldState.drawerState.close()
                }
            }
        }

    }
}

@Composable
fun DrawerItem(
    item: AppView,
    selected: Boolean,
    onItemClick: (AppView) -> Unit
) {
    Spacer(modifier = Modifier.height(2.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
//            .padding(8.dp) //Padding exterior
            .clip(RoundedCornerShape(0, 50, 50, 0))
            .background(if (selected) PrimaryVariant else Color.Transparent)
//            .padding(8.dp) //Padding interior
            .clickable { onItemClick(item) },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(modifier = Modifier.width(12.dp))
        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = item.icon,
            contentDescription = stringResource(id = item.title),
            tint = if (selected) Color.White else PrimaryVariant,

        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = stringResource(id = item.title),
            style = TextStyle(fontSize = 14.sp),
            color = if (selected) Color.White else PrimaryVariant
        )
    }
}