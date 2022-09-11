package cu.osmel.tvplus.domain.usecase.season

import cu.osmel.tvplus.data.repository.SeasonRepository
import cu.osmel.tvplus.domain.mappers.season.toDomain
import cu.osmel.tvplus.domain.model.Season
import javax.inject.Inject

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
class GetSeasonUseCase @Inject constructor(private val repository: SeasonRepository) {
    suspend operator fun invoke(id: Long): Season? {
        return repository.get(id)?.toDomain()
    }
}