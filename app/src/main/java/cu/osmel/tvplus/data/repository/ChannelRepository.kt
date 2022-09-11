package cu.osmel.tvplus.data.repository

import android.database.SQLException
import cu.osmel.tvplus.data.database.dao.ChannelDao
import cu.osmel.tvplus.data.database.entities.ChannelEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
class ChannelRepository @Inject constructor(private val channelDao: ChannelDao) {

    fun getAll(): Flow<List<ChannelEntity>> {
        return channelDao.getAll()
    }

    @Throws(SQLException::class)
    suspend fun insert(channel: ChannelEntity): Long {
        return channelDao.insert(channel)
    }

    suspend fun insertAll(list: List<ChannelEntity>) {
        channelDao.insertAll(list)
    }

    suspend fun update(channel: ChannelEntity) {
        channelDao.update(channel)
    }

    suspend fun delete(channel: ChannelEntity) {
        channelDao.delete(channel)
    }

    suspend fun delete(list: List<ChannelEntity>) {
        channelDao.delete(list)
    }

    fun search(query: String): Flow<List<ChannelEntity>> {
        return channelDao.search(query = query)
    }

    suspend fun get(id: Long): ChannelEntity? {
        return channelDao.get(id)
    }

    suspend fun getChannelByName(name: String): ChannelEntity? {
        return channelDao.getChannelByName(name = name)
    }

    suspend fun existsName(name: String): Boolean {
        return channelDao.existsName(name = name)
    }
}