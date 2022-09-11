package cu.osmel.tvplus.data.database.dao

import android.database.SQLException
import androidx.room.*
import cu.osmel.tvplus.data.database.entities.CountryEntity
import kotlinx.coroutines.flow.Flow

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
@Dao
interface CountryDao {
    @Query("SELECT * from country ORDER BY id_country ASC")
    fun getAll(): Flow<List<CountryEntity>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    @Throws(SQLException::class)
    suspend fun insert(country: CountryEntity): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAll(list: List<CountryEntity>)

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun update(country: CountryEntity)

    @Delete
    suspend fun delete(country: CountryEntity)

    @Delete
    suspend fun delete(list: List<CountryEntity>)

    @Query(
        """ SELECT * FROM country WHERE 
        name LIKE :query
        ORDER BY name ASC """
    )
    fun search(query: String): Flow<List<CountryEntity>>

    @Query("SELECT * FROM country WHERE id_country = :id LIMIT 1")
    suspend fun get(id: Long): CountryEntity?

    @Query("SELECT * FROM country WHERE name = :name LIMIT 1")
    suspend fun getCountryByName(name: String): CountryEntity?

    @Query("SELECT EXISTS(SELECT * FROM country WHERE name = :name)")
    suspend fun existsName(name: String): Boolean
}