package cu.osmel.tvplus.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import cu.osmel.tvplus.ui.theme.TvPlusTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import cu.osmel.tvplus.ui.componets.Drawer
import cu.osmel.tvplus.ui.componets.topbar.TopBar
import cu.osmel.tvplus.ui.navegation.AppView
import cu.osmel.tvplus.ui.navegation.NavigationHost
import cu.osmel.tvplus.ui.theme.SecondaryVariant
import cu.osmel.tvplus.ui.viewmodel.common.MainViewModel

@ExperimentalPagerApi
@ExperimentalMaterialApi
@AndroidEntryPoint
@ExperimentalComposeUiApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = rememberSystemUiController()
            val darkMode = remember { mutableStateOf(false) }

            SideEffect {
                systemUiController.setStatusBarColor(
                    color = SecondaryVariant
                )
            }

            TvPlusTheme(
                darkTheme = darkMode.value
            ) {
                Surface(color = MaterialTheme.colors.surface) {
                    MainScreen(darkMode)
                }
            }
        }
    }
}

@ExperimentalPagerApi
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun MainScreen(darkMode: MutableState<Boolean>) {
    val viewModel = MainViewModel()
    val currentScreen by viewModel.currentScreen.observeAsState()
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState(
        drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    )
    val scope = rememberCoroutineScope()

    val navigationItems = listOf(
        AppView.HomeView,
        AppView.GenreView,
        AppView.CountryView,
        AppView.ChannelView,
        AppView.TvShowView,
        AppView.SeasonView,
        AppView.SettingView
    )

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(
                scope,
                scaffoldState,
                currentScreen!!.title,
                actions = viewModel.appBarActions
            )
        },
        drawerContent = { Drawer(scope, scaffoldState, navController, items = navigationItems) },
        drawerGesturesEnabled = true
    ) {
        NavigationHost(navController, darkMode, viewModel = viewModel)
    }
}