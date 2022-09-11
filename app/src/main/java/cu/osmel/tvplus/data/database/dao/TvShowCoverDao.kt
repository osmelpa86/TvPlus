package cu.osmel.tvplus.data.database.dao

import androidx.room.*
import cu.osmel.tvplus.data.database.entities.TvShowCoverEntity
import kotlinx.coroutines.flow.Flow

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
@Dao
interface TvShowCoverDao {
    @Query("SELECT * from cover_show ORDER BY id_tv_show_cover DESC")
    fun getAll(): Flow<List<TvShowCoverEntity>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(coverShow: TvShowCoverEntity): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAll(list: List<TvShowCoverEntity>)

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun update(coverShow: TvShowCoverEntity)

    @Delete
    suspend fun delete(coverShow: TvShowCoverEntity)

    @Delete
    suspend fun delete(list: List<TvShowCoverEntity>)

    @Query("SELECT * FROM cover_show WHERE id_tv_show_cover = :id LIMIT 1")
    suspend fun get(id: Long): TvShowCoverEntity
}