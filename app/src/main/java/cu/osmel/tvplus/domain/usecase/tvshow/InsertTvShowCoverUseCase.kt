package cu.osmel.tvplus.domain.usecase.tvshow

import android.database.SQLException
import cu.osmel.tvplus.data.database.entities.TvShowCoverEntity
import cu.osmel.tvplus.data.repository.TvShowCoverRepository
import javax.inject.Inject
import kotlin.Throws

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
class InsertTvShowCoverUseCase @Inject constructor(private val repository: TvShowCoverRepository) {
    @Throws(SQLException::class)
    suspend operator fun invoke(tvShowCover: TvShowCoverEntity): Long {
        return repository.insert(tvShowCover = tvShowCover)
    }
}