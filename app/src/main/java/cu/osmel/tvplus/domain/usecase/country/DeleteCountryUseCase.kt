package cu.osmel.tvplus.domain.usecase.country

import cu.osmel.tvplus.data.database.entities.CountryEntity
import cu.osmel.tvplus.data.repository.CountryRepository
import javax.inject.Inject

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
class DeleteCountryUseCase @Inject constructor(private val repository: CountryRepository) {
    suspend operator fun invoke(country: CountryEntity) {
        return repository.delete(country)
    }
}