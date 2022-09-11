package cu.osmel.tvplus.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import cu.osmel.tvplus.data.database.entities.EpisodeEntity

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
@Dao
interface EpisodeDao {
    @Query("SELECT * from episode ORDER BY name DESC")
    suspend fun getAll(): List<EpisodeEntity>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(episode: EpisodeEntity): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAll(list: List<EpisodeEntity>)

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun update(episode: EpisodeEntity)

    @Delete
    suspend fun delete(episode: EpisodeEntity)

    @Delete
    suspend fun delete(list: List<EpisodeEntity>)

    @Query(
        """ SELECT * FROM episode WHERE 
        name LIKE :query OR number LIKE :query OR date LIKE :query
        ORDER BY name ASC """
    )
    fun search(query: String): LiveData<List<EpisodeEntity>>

    @Query("SELECT * FROM episode WHERE id_episode = :id LIMIT 1")
    fun get(id: Long): LiveData<EpisodeEntity>
}