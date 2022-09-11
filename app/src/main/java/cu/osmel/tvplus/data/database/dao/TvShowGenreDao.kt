package cu.osmel.tvplus.data.database.dao

import androidx.room.*
import cu.osmel.tvplus.data.database.entities.TvShowGenreEntity
import kotlinx.coroutines.flow.Flow

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
@Dao
interface TvShowGenreDao {
    @Query("SELECT * from genre_show ORDER BY id_tv_show_genre DESC")
    fun getAll(): Flow<List<TvShowGenreEntity>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(genre_show: TvShowGenreEntity): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAll(list: List<TvShowGenreEntity>)

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun update(genre_show: TvShowGenreEntity)

    @Delete
    suspend fun delete(genre_show: TvShowGenreEntity)

    @Delete
    suspend fun delete(list: List<TvShowGenreEntity>)

    @Query("SELECT * FROM genre_show WHERE id_tv_show_genre = :id LIMIT 1")
    suspend fun get(id: Long): TvShowGenreEntity
}