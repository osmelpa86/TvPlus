package cu.osmel.tvplus.domain.usecase.season

import android.database.SQLException
import cu.osmel.tvplus.data.database.entities.SeasonEntity
import cu.osmel.tvplus.data.repository.SeasonRepository
import javax.inject.Inject
import kotlin.Throws

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
class InsertSeasonUseCase @Inject constructor(private val repository: SeasonRepository) {
    @Throws(SQLException::class)
    suspend operator fun invoke(season: SeasonEntity):Long {
        return repository.insert(season = season)
    }
}