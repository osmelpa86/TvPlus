package cu.osmel.tvplus.ui.view.tvshow.state

import cu.osmel.tvplus.domain.model.TvShow

/**
 * Created by Osmel Pérez Alzola(osmelpa86@gmail.com) on 6/6/22
 */
data class TvShowState(
    val listTvShows: List<TvShow> = emptyList(),
)
