package cu.osmel.tvplus.domain.usecase.country

import cu.osmel.tvplus.data.database.entities.CountryEntity
import cu.osmel.tvplus.data.repository.CountryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
class GetAllCountryUseCase @Inject constructor(private val repository: CountryRepository) {
    operator fun invoke(): Flow<List<CountryEntity>> {
        return repository.getAll()
    }
}