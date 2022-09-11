package cu.osmel.tvplus.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import cu.osmel.tvplus.data.database.entities.PersonEntity

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
@Dao
interface PersonDao {
    @Query("SELECT * from person ORDER BY name DESC")
    suspend fun getAll(): List<PersonEntity>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(person: PersonEntity): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAll(list: List<PersonEntity>)

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun update(person: PersonEntity)

    @Delete
    suspend fun delete(person: PersonEntity)

    @Delete
    suspend fun delete(list: List<PersonEntity>)

    @Query(
        """ SELECT * FROM person WHERE 
        name LIKE :query OR
        sex LIKE :query OR
        type LIKE :query
        ORDER BY name ASC """
    )
    fun search(query: String): LiveData<List<PersonEntity>>

    @Query("SELECT * FROM person WHERE id_person = :id LIMIT 1")
    fun get(id: Long): LiveData<PersonEntity>
}