package cu.osmel.tvplus.domain.usecase.channel

import cu.osmel.tvplus.data.database.entities.ChannelEntity
import cu.osmel.tvplus.data.repository.ChannelRepository
import javax.inject.Inject

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
class DeleteChannelUseCase @Inject constructor(private val repository: ChannelRepository) {
    suspend operator fun invoke(channel: ChannelEntity) {
        return repository.delete(channel)
    }
}