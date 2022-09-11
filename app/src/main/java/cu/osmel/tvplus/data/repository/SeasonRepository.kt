package cu.osmel.tvplus.data.repository

import android.database.SQLException
import cu.osmel.tvplus.data.database.dao.SeasonDao
import cu.osmel.tvplus.data.database.entities.SeasonEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.Throws

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
class SeasonRepository @Inject constructor(private val seasonDao: SeasonDao) {

    fun getAll(): Flow<List<SeasonEntity>> {
        return seasonDao.getAll()
    }

    @Throws(SQLException::class)
    suspend fun insert(season: SeasonEntity): Long {
        return seasonDao.insert(season)
    }

    suspend fun insertAll(list: List<SeasonEntity>) {
        seasonDao.insertAll(list)
    }

    suspend fun update(season: SeasonEntity) {
        seasonDao.update(season)
    }

    suspend fun delete(season: SeasonEntity) {
        seasonDao.delete(season)
    }

    suspend fun delete(list: List<SeasonEntity>) {
        seasonDao.delete(list)
    }

    fun search(query: String): Flow<List<SeasonEntity>> {
        return seasonDao.search(query = query)
    }

    suspend fun get(id: Long): SeasonEntity? {
        return seasonDao.get(id = id)
    }
}