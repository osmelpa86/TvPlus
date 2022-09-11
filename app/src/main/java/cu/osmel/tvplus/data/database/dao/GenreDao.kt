package cu.osmel.tvplus.data.database.dao

import android.database.SQLException
import androidx.room.*
import cu.osmel.tvplus.data.database.entities.GenreEntity
import kotlinx.coroutines.flow.Flow
import kotlin.Throws

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
@Dao
interface GenreDao {
    @Query("SELECT * from genre ORDER BY id_genre ASC")
    fun getAll(): Flow<List<GenreEntity>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    @Throws(SQLException::class)
    suspend fun insert(genre: GenreEntity): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAll(list: List<GenreEntity>)

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun update(genre: GenreEntity)

    @Delete
    suspend fun delete(genre: GenreEntity)

    @Delete
    suspend fun delete(list: List<GenreEntity>)

    @Query(
        """ SELECT * FROM genre WHERE 
        name LIKE :query
        ORDER BY name ASC """
    )
    fun search(query: String): Flow<List<GenreEntity>>

    @Query("SELECT * FROM genre WHERE id_genre = :id LIMIT 1")
    suspend fun get(id: Long): GenreEntity?

    @Query("SELECT * FROM genre WHERE name = :name LIMIT 1")
    suspend fun getGenreByName(name: String): GenreEntity?

    @Query("SELECT EXISTS(SELECT * FROM genre WHERE name = :name)")
    suspend fun existsName(name: String): Boolean
}