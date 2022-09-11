package cu.osmel.tvplus.data.repository

import android.database.SQLException
import cu.osmel.tvplus.data.database.dao.TvShowChannelDao
import cu.osmel.tvplus.data.database.entities.TvShowChannelEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.Throws

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
class TvShowChannelRepository @Inject constructor(private val tvShowChannelDao: TvShowChannelDao) {

    fun getAll(): Flow<List<TvShowChannelEntity>> {
        return tvShowChannelDao.getAll()
    }

    @Throws(SQLException::class)
    suspend fun insert(tvShowChannel: TvShowChannelEntity): Long {
        return tvShowChannelDao.insert(tvShowChannel)
    }

    suspend fun insertAll(list: List<TvShowChannelEntity>) {
        tvShowChannelDao.insertAll(list)
    }

    suspend fun update(tvShowChannel: TvShowChannelEntity) {
        tvShowChannelDao.update(tvShowChannel)
    }

    suspend fun delete(tvShowChannel: TvShowChannelEntity) {
        tvShowChannelDao.delete(tvShowChannel)
    }

    suspend fun delete(list: List<TvShowChannelEntity>) {
        tvShowChannelDao.delete(list)
    }

    suspend fun get(id: Long): TvShowChannelEntity? {
        return tvShowChannelDao.get(id = id)
    }
}