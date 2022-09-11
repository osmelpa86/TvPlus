package cu.osmel.tvplus.domain.usecase.season

import cu.osmel.tvplus.data.database.entities.SeasonEntity
import cu.osmel.tvplus.data.repository.SeasonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
class GetAllSeasonUseCase @Inject constructor(private val repository: SeasonRepository) {
    operator fun invoke(): Flow<List<SeasonEntity>> {
        return repository.getAll()
    }
}