package cu.osmel.tvplus.domain.usecase.country

import cu.osmel.tvplus.data.repository.CountryRepository
import cu.osmel.tvplus.domain.mappers.country.toDomain
import cu.osmel.tvplus.domain.model.Country
import javax.inject.Inject

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
class GetCountryByNameUseCase @Inject constructor(private val repository: CountryRepository) {
    suspend operator fun invoke(name: String): Country? {
        return repository.getCountryByName(name)?.toDomain()
    }
}