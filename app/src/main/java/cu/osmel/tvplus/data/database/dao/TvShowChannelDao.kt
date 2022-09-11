package cu.osmel.tvplus.data.database.dao

import androidx.room.*
import cu.osmel.tvplus.data.database.entities.TvShowChannelEntity
import kotlinx.coroutines.flow.Flow

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
@Dao
interface TvShowChannelDao {
    @Query("SELECT * from channel_show ORDER BY id_tv_show_channel DESC")
    fun getAll(): Flow<List<TvShowChannelEntity>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(channelShow: TvShowChannelEntity): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAll(list: List<TvShowChannelEntity>)

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun update(channelShow: TvShowChannelEntity)

    @Delete
    suspend fun delete(channelShow: TvShowChannelEntity)

    @Delete
    suspend fun delete(list: List<TvShowChannelEntity>)

    @Query("SELECT * FROM channel_show WHERE id_tv_show_channel = :id LIMIT 1")
    suspend fun get(id: Long): TvShowChannelEntity
}