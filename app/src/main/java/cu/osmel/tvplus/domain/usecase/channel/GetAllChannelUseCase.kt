package cu.osmel.tvplus.domain.usecase.channel

import cu.osmel.tvplus.data.database.entities.ChannelEntity
import cu.osmel.tvplus.data.repository.ChannelRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
class GetAllChannelUseCase @Inject constructor(private val repository: ChannelRepository) {
    operator fun invoke(): Flow<List<ChannelEntity>> {
        return repository.getAll()
    }
}