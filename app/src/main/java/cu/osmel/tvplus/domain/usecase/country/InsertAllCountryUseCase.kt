package cu.osmel.tvplus.domain.usecase.country

import android.database.SQLException
import cu.osmel.tvplus.data.database.entities.CountryEntity
import cu.osmel.tvplus.data.repository.CountryRepository
import javax.inject.Inject
import kotlin.Throws

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
class InsertAllCountryUseCase @Inject constructor(private val repository: CountryRepository) {
    @Throws(SQLException::class)
    suspend operator fun invoke(list: List<CountryEntity>) {
        return repository.insertAll(list = list)
    }
}