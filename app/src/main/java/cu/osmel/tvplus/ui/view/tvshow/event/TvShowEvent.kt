package cu.osmel.tvplus.ui.view.tvshow.event

import cu.osmel.tvplus.domain.model.TvShow

sealed class TvShowEvent {
    data class InsertTvShow(val tvShow: TvShow) : TvShowEvent()
    data class DeleteTvShow(val tvShow: TvShow) : TvShowEvent()
    data class UpdateTvShow(val tvShow: TvShow) : TvShowEvent()
    data class LoadByIdTvShow(val id: Long) : TvShowEvent()
}