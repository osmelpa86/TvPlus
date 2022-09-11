package cu.osmel.tvplus.data.database.dao

import androidx.room.*
import cu.osmel.tvplus.data.database.entities.CoverEntity
import kotlinx.coroutines.flow.Flow

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
@Dao
interface CoverDao {
    @Query("SELECT * from cover ORDER BY id_cover DESC")
    @Transaction
    fun getAll(): Flow<List<CoverEntity>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(cover: CoverEntity): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAll(list: List<CoverEntity>)

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun update(cover: CoverEntity)

    @Delete
    suspend fun delete(cover: CoverEntity)

    @Delete
    fun delete(list: List<CoverEntity>)

    @Query("SELECT * FROM cover WHERE id_cover = :id LIMIT 1")
    fun get(id: Long): CoverEntity?
}