package cu.osmel.tvplus.data.database.dao

import androidx.room.*
import cu.osmel.tvplus.data.database.entities.TvShowEntity
import cu.osmel.tvplus.data.database.pojo.TvShowPojo
import kotlinx.coroutines.flow.Flow

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
@Dao
interface TvShowDao {
    @Query("SELECT * from tv_show ORDER BY title DESC")
    @Transaction
    fun getAll(): Flow<List<TvShowPojo>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(tvShow: TvShowEntity): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAll(list: List<TvShowEntity>)

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun update(tvShow: TvShowEntity)

    @Delete
    suspend fun delete(tvShow: TvShowEntity)

    @Delete
    suspend fun delete(list: List<TvShowEntity>)

    @Transaction
    @Query(
        """ SELECT * FROM tv_show WHERE 
        title LIKE :query OR
        year LIKE :query OR
        duration LIKE :query OR
        type LIKE :query OR
        synopsis LIKE :query OR
        status LIKE :query
        ORDER BY title ASC """
    )
    fun search(query: String): Flow<List<TvShowPojo>>

    @Transaction
    @Query("SELECT * FROM tv_show WHERE id_tv_show = :id LIMIT 1")
    suspend fun get(id: Long): TvShowPojo?
}