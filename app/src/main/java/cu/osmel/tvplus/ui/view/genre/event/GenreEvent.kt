package cu.osmel.tvplus.ui.view.genre.event

import cu.osmel.tvplus.domain.model.Genre

/**
 * Created by Osmel Pérez Alzola(osmelpa86@gmail.com) on 18/5/22
 */
sealed class GenreEvent {
    data class InsertGenre(val genre: Genre) : GenreEvent()
    data class InsertAllGenre(val list: List<Genre>) : GenreEvent()
    data class DeleteGenre(val genre: Genre) : GenreEvent()
    data class UpdateEvent(val genre: Genre) : GenreEvent()
}

