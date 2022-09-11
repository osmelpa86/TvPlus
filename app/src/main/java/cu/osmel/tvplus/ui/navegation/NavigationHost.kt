package cu.osmel.tvplus.ui.navegation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.pager.ExperimentalPagerApi
import cu.osmel.tvplus.ui.view.country.CountryView
import cu.osmel.tvplus.ui.view.genre.GenreView
import cu.osmel.tvplus.ui.view.home.HomeView
import cu.osmel.tvplus.ui.view.channel.ChannelView
import cu.osmel.tvplus.ui.view.season.SeasonView
import cu.osmel.tvplus.ui.view.settings.SettingView
import cu.osmel.tvplus.ui.view.tvshow.ManageTvShowView
import cu.osmel.tvplus.ui.view.tvshow.TvShowView
import cu.osmel.tvplus.ui.viewmodel.common.MainViewModel

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun NavigationHost(
    navController: NavHostController,
    darkMode: MutableState<Boolean>,
    viewModel: MainViewModel,
) {
    NavHost(navController = navController, startDestination = AppView.TvShowView.route) {
        composable(AppView.HomeView) {
            HomeView(darkMode = darkMode, viewModel = viewModel)
        }

        composable(AppView.GenreView) {
            GenreView(darkMode = darkMode, viewModel = viewModel)
        }

        composable(AppView.CountryView) {
            CountryView(darkMode = darkMode, viewModel = viewModel)
        }

        composable(AppView.ChannelView.route) {
            ChannelView(darkMode = darkMode, viewModel = viewModel)
        }

        composable(AppView.TvShowView.route) {
            TvShowView(
                darkMode = darkMode,
                viewModel = viewModel,
                onAddClick = {
                    navController.navigate(AppView.ManageTvShowView.route)
                },
                onItemClick = {
                    navController.navigate(AppView.ManageTvShowView.createNavRoute(it))
                }
            )
        }

        composable(AppView.ManageTvShowView) { backStackEntry ->
            val tvShowId: String = backStackEntry.findArg(arg = NavArg.TvShowId)
            ManageTvShowView(
                darkMode = darkMode,
                viewModel = viewModel,
                onCancelClick = {
                    navController.navigate(AppView.TvShowView.route)
                },
                tvShowId = if (!tvShowId.equals("{tvShowId}")) tvShowId.toLong() else null
            )
        }

        composable(AppView.SeasonView.route) {
            SeasonView(darkMode = darkMode, viewModel = viewModel)
        }

        composable(AppView.SettingView.route) {
            SettingView(darkMode = darkMode, viewModel = viewModel)
        }
    }
}

private fun NavGraphBuilder.composable(
    navItem: AppView,
    content: @Composable (NavBackStackEntry) -> Unit,
) {
    composable(
        route = navItem.route,
        arguments = navItem.args
    ) {
        content(it)
    }
}

private inline fun <reified T> NavBackStackEntry.findArg(arg: NavArg): T {
    val value = arguments?.get(arg.key)
    requireNotNull(value)
    return value as T
}