package cu.osmel.tvplus.domain.usecase.tvshow

import android.database.SQLException
import cu.osmel.tvplus.data.database.entities.TvShowGenreEntity
import cu.osmel.tvplus.data.repository.TvShowGenreRepository
import javax.inject.Inject
import kotlin.Throws

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
class InsertTvShowGenreUseCase @Inject constructor(private val repository: TvShowGenreRepository) {
    @Throws(SQLException::class)
    suspend operator fun invoke(tvShowGenre: TvShowGenreEntity): Long {
        return repository.insert(tvShowGenre = tvShowGenre)
    }
}