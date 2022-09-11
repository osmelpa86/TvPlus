package cu.osmel.tvplus.ui.navegation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument
import cu.osmel.tvplus.R

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
sealed class AppView(
    val baseRoute: String,
    val title: Int,
    val icon: ImageVector,
    val navArgs: List<NavArg> = emptyList(),
) {
    val route = run {
        //baseRoute/{arg1}/{arg2}
        val argValues = navArgs.map { "{${it.key}}" }
        listOf(baseRoute)
            .plus(argValues)
            .joinToString("/")
    }

    val args = navArgs.map { navArgument(it.key) { it.navType } }

    object HomeView : AppView("home_view", R.string.home, Icons.Rounded.Home)
    object GenreView : AppView("genre_view", R.string.genre, Icons.Rounded.TheaterComedy)
    object CountryView : AppView("country_view", R.string.country, Icons.Rounded.Place)
    object ChannelView : AppView("channel_view", R.string.channel, Icons.Rounded.CastConnected)
    object TvShowView :
        AppView("tv_show_view", R.string.tv_show, Icons.Rounded.LiveTv)

    object ManageTvShowView :
        AppView(
            baseRoute = "manage_tv_show_view",
            title = R.string.manage_tv_show,
            icon = Icons.Rounded.LiveTv,
            navArgs = listOf(NavArg.TvShowId)) {
        fun createNavRoute(tvShowId: Long) = "$baseRoute/$tvShowId"
    }

    object SeasonView : AppView("season_view", R.string.season, Icons.Rounded.Event)
    object SettingView : AppView("settings_view", R.string.settings, Icons.Rounded.Settings)
}

enum class NavArg(val key: String, val navType: NavType<*>) {
    TvShowId("tvShowId", NavType.LongType)
}
