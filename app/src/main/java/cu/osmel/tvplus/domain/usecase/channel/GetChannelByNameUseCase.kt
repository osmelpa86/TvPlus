package cu.osmel.tvplus.domain.usecase.channel

import cu.osmel.tvplus.data.repository.ChannelRepository
import cu.osmel.tvplus.domain.mappers.channel.toDomain
import cu.osmel.tvplus.domain.model.Channel
import javax.inject.Inject

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
class GetChannelByNameUseCase @Inject constructor(private val repository: ChannelRepository) {
    suspend operator fun invoke(name: String): Channel? {
        return repository.getChannelByName(name)?.toDomain()
    }
}