package cu.osmel.tvplus.ui.componets.topbar

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun TopBar(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    title: Int,
    actions: LiveData<List<ActionItem>>
) {
    TopAppBar(
        title = { Text(stringResource(id = title), color = Color.White) },
        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    scaffoldState.drawerState.open()
                }
            }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Menu Icon",
                    tint = Color.White
                )
            }
        },
        actions = {
            val listActions = actions.observeAsState().value
            if (listActions?.isNotEmpty() == true) {
                listActions.forEach { action ->
                    IconButton(
                        onClick = action.onClick,
                        enabled = action.enabled
                    ) {
                        Icon(
                            imageVector = action.icon,
                            contentDescription = null,
                            tint = if (action.enabled) Color.White else Color.Gray
                        )
                    }
                }
            }
        }
    )
}