package cu.osmel.tvplus.data.database.dao

import androidx.room.*
import cu.osmel.tvplus.data.database.entities.SeasonEntity
import kotlinx.coroutines.flow.Flow

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
@Dao
interface SeasonDao {
    @Query("SELECT * from season ORDER BY id_season DESC")
    fun getAll(): Flow<List<SeasonEntity>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(season: SeasonEntity): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAll(list: List<SeasonEntity>)

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun update(season: SeasonEntity)

    @Delete
    suspend fun delete(season: SeasonEntity)

    @Delete
    suspend fun delete(list: List<SeasonEntity>)

    @Query(
        """ SELECT * FROM season WHERE 
        number LIKE :query OR
        total_chapters LIKE :query OR
        status LIKE :query OR
        year LIKE :query
        ORDER BY id_season ASC """
    )
    fun search(query: String): Flow<List<SeasonEntity>>

    @Query("SELECT * FROM season WHERE id_season = :id LIMIT 1")
    suspend fun get(id: Long): SeasonEntity?
}