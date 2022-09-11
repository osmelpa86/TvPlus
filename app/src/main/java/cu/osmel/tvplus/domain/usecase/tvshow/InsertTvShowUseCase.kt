package cu.osmel.tvplus.domain.usecase.tvshow

import android.database.SQLException
import cu.osmel.tvplus.data.database.entities.TvShowEntity
import cu.osmel.tvplus.data.repository.TvShowRepository
import javax.inject.Inject
import kotlin.Throws

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
class InsertTvShowUseCase @Inject constructor(private val repository: TvShowRepository) {
    @Throws(SQLException::class)
    suspend operator fun invoke(tvShow: TvShowEntity):Long {
        return repository.insert(tvShow = tvShow)
    }
}