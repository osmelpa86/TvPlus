package cu.osmel.tvplus.domain.usecase.country

import android.database.SQLException
import cu.osmel.tvplus.data.repository.CountryRepository
import javax.inject.Inject
import kotlin.Throws

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
class ExistCountryByNameUseCase @Inject constructor(private val repository: CountryRepository) {
    @Throws(SQLException::class)
    suspend operator fun invoke(name: String): Boolean {
        return repository.existsName(name = name)
    }
}