package cu.osmel.tvplus.domain.usecase.channel

import android.database.SQLException
import cu.osmel.tvplus.data.repository.ChannelRepository
import javax.inject.Inject
import kotlin.Throws

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
class ExistChannelByNameUseCase @Inject constructor(private val repository: ChannelRepository) {
    @Throws(SQLException::class)
    suspend operator fun invoke(name: String): Boolean {
        return repository.existsName(name = name)
    }
}