package cu.osmel.tvplus.ui.view.season.event

import cu.osmel.tvplus.domain.model.Season

/**
 * Created by Osmel Pérez Alzola(osmelpa86@gmail.com) on 18/5/22
 */
sealed class SeasonEvent {
    data class InsertSeason(val season: Season) : SeasonEvent()
    data class InsertAllSeason(val list: List<Season>) : SeasonEvent()
    data class DeleteSeason(val season: Season) : SeasonEvent()
    data class UpdateEvent(val season: Season) : SeasonEvent()
}

