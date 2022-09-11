package cu.osmel.tvplus.data.repository

import android.database.SQLException
import cu.osmel.tvplus.data.database.dao.CountryDao
import cu.osmel.tvplus.data.database.entities.CountryEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.Throws

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
class CountryRepository @Inject constructor(private val countryDao: CountryDao) {

    fun getAll(): Flow<List<CountryEntity>> {
        return countryDao.getAll()
    }

    @Throws(SQLException::class)
    suspend fun insert(country: CountryEntity): Long {
        return countryDao.insert(country)
    }

    suspend fun insertAll(list: List<CountryEntity>) {
        countryDao.insertAll(list)
    }

    suspend fun update(country: CountryEntity) {
        countryDao.update(country)
    }

    suspend fun delete(country: CountryEntity) {
        countryDao.delete(country)
    }

    suspend fun delete(list: List<CountryEntity>) {
        countryDao.delete(list)
    }

    fun search(query: String): Flow<List<CountryEntity>> {
        return countryDao.search(query = query)
    }

    suspend fun get(id: Long): CountryEntity? {
        return countryDao.get(id = id)
    }

    suspend fun getCountryByName(name: String): CountryEntity? {
        return countryDao.getCountryByName(name = name)
    }

    suspend fun existsName(name: String): Boolean {
        return countryDao.existsName(name = name)
    }
}