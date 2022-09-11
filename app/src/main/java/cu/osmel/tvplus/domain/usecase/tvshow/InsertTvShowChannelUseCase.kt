package cu.osmel.tvplus.domain.usecase.tvshow

import android.database.SQLException
import cu.osmel.tvplus.data.database.entities.TvShowChannelEntity
import cu.osmel.tvplus.data.database.entities.TvShowGenreEntity
import cu.osmel.tvplus.data.repository.TvShowChannelRepository
import cu.osmel.tvplus.data.repository.TvShowGenreRepository
import javax.inject.Inject
import kotlin.Throws

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
class InsertTvShowChannelUseCase @Inject constructor(private val repository: TvShowChannelRepository) {
    @Throws(SQLException::class)
    suspend operator fun invoke(tvShowChannel: TvShowChannelEntity): Long {
        return repository.insert(tvShowChannel = tvShowChannel)
    }
}