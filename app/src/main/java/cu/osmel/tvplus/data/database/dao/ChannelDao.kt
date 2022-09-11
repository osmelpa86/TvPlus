package cu.osmel.tvplus.data.database.dao

import android.database.SQLException
import androidx.room.*
import cu.osmel.tvplus.data.database.entities.ChannelEntity
import kotlinx.coroutines.flow.Flow

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
@Dao
interface ChannelDao {
    @Query("SELECT * from channel ORDER BY name DESC")
    fun getAll(): Flow<List<ChannelEntity>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    @Throws(SQLException::class)
    suspend fun insert(channel: ChannelEntity): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAll(list: List<ChannelEntity>)

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun update(channel: ChannelEntity)

    @Delete
    suspend fun delete(channel: ChannelEntity)

    @Delete
    suspend fun delete(list: List<ChannelEntity>)

    @Query(
        """ SELECT * FROM channel WHERE 
        name LIKE :query
        ORDER BY name ASC """
    )
    fun search(query: String): Flow<List<ChannelEntity>>

    @Query("SELECT * FROM channel WHERE id_channel = :id LIMIT 1")
    suspend fun get(id: Long): ChannelEntity?

    @Query("SELECT * FROM channel WHERE name = :name LIMIT 1")
    suspend fun getChannelByName(name: String): ChannelEntity?

    @Query("SELECT EXISTS(SELECT * FROM channel WHERE name = :name)")
    suspend fun existsName(name: String): Boolean
}