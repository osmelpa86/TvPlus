package cu.osmel.tvplus.domain.usecase.channel

import android.database.SQLException
import cu.osmel.tvplus.data.database.entities.ChannelEntity
import cu.osmel.tvplus.data.repository.ChannelRepository
import javax.inject.Inject
import kotlin.Throws

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
class InsertChannelUseCase @Inject constructor(private val repository: ChannelRepository) {
    @Throws(SQLException::class)
    suspend operator fun invoke(channel: ChannelEntity):Long {
        return repository.insert(channel = channel)
    }
}