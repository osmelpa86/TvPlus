package cu.osmel.tvplus.ui.view.genre.state

import cu.osmel.tvplus.domain.model.Genre

/**
 * Created by Osmel Pérez Alzola(osmelpa86@gmail.com) on 18/5/22
 */
data class GenreState(
    val listGenres: List<Genre> = emptyList()
)
