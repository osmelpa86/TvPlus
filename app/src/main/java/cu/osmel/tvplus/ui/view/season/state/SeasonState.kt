package cu.osmel.tvplus.ui.view.season.state

import cu.osmel.tvplus.domain.model.Season

/**
 * Created by Osmel Pérez Alzola(osmelpa86@gmail.com) on 18/5/22
 */
data class SeasonState(
    val listSeasons: List<Season> = emptyList()
)
