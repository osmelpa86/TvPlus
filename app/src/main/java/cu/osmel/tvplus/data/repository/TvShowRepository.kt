package cu.osmel.tvplus.data.repository

import android.database.SQLException
import cu.osmel.tvplus.data.database.dao.TvShowDao
import cu.osmel.tvplus.data.database.entities.TvShowEntity
import cu.osmel.tvplus.data.database.pojo.TvShowPojo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.Throws

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
class TvShowRepository @Inject constructor(private val tvShowDao: TvShowDao) {

    fun getAll(): Flow<List<TvShowPojo>> {
        return tvShowDao.getAll()
    }

    @Throws(SQLException::class)
    suspend fun insert(tvShow: TvShowEntity): Long {
        return tvShowDao.insert(tvShow)
    }

    suspend fun insertAll(list: List<TvShowEntity>) {
        tvShowDao.insertAll(list)
    }

    suspend fun update(tvShow: TvShowEntity) {
        tvShowDao.update(tvShow)
    }

    suspend fun delete(tvShow: TvShowEntity) {
        tvShowDao.delete(tvShow)
    }

    suspend fun delete(list: List<TvShowEntity>) {
        tvShowDao.delete(list)
    }

    fun search(query: String): Flow<List<TvShowPojo>> {
        return tvShowDao.search(query = query)
    }

    suspend fun get(id: Long): TvShowPojo? {
        return tvShowDao.get(id = id)
    }
}