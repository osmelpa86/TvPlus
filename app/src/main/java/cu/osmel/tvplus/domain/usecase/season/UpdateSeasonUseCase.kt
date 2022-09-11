package cu.osmel.tvplus.domain.usecase.season

import cu.osmel.tvplus.data.database.entities.SeasonEntity
import cu.osmel.tvplus.data.repository.SeasonRepository
import javax.inject.Inject

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
class UpdateSeasonUseCase @Inject constructor(private val repository: SeasonRepository) {
    suspend operator fun invoke(season: SeasonEntity) {
        return repository.update(season = season)
    }
}