package cu.osmel.tvplus.data.repository

import android.database.SQLException
import cu.osmel.tvplus.data.database.dao.TvShowCoverDao
import cu.osmel.tvplus.data.database.entities.TvShowCoverEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.Throws

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
class TvShowCoverRepository @Inject constructor(private val tvShowCoverDao: TvShowCoverDao) {

    fun getAll(): Flow<List<TvShowCoverEntity>> {
        return tvShowCoverDao.getAll()
    }

    @Throws(SQLException::class)
    suspend fun insert(tvShowCover: TvShowCoverEntity): Long {
        return tvShowCoverDao.insert(tvShowCover)
    }

    suspend fun insertAll(list: List<TvShowCoverEntity>) {
        tvShowCoverDao.insertAll(list)
    }

    suspend fun update(tvShowCover: TvShowCoverEntity) {
        tvShowCoverDao.update(tvShowCover)
    }

    suspend fun delete(tvShowCover: TvShowCoverEntity) {
        tvShowCoverDao.delete(tvShowCover)
    }

    suspend fun delete(list: List<TvShowCoverEntity>) {
        tvShowCoverDao.delete(list)
    }

    suspend fun get(id: Long): TvShowCoverEntity? {
        return tvShowCoverDao.get(id = id)
    }
}