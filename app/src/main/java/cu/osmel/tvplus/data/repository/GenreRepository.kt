package cu.osmel.tvplus.data.repository

import android.database.SQLException
import cu.osmel.tvplus.data.database.dao.GenreDao
import cu.osmel.tvplus.data.database.entities.GenreEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.Throws

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
class GenreRepository @Inject constructor(private val genreDao: GenreDao) {

    fun getAll(): Flow<List<GenreEntity>> {
        return genreDao.getAll()
    }

    @Throws(SQLException::class)
    suspend fun insert(genre: GenreEntity): Long {
        return genreDao.insert(genre)
    }

    suspend fun insertAll(list: List<GenreEntity>) {
        genreDao.insertAll(list)
    }

    suspend fun update(genre: GenreEntity) {
        genreDao.update(genre)
    }

    suspend fun delete(genre: GenreEntity) {
        genreDao.delete(genre)
    }

    suspend fun delete(list: List<GenreEntity>) {
        genreDao.delete(list)
    }

    fun search(query: String): Flow<List<GenreEntity>> {
        return genreDao.search(query = query)
    }

    suspend fun get(id: Long): GenreEntity? {
        return genreDao.get(id = id)
    }

    suspend fun getGenreByName(name: String): GenreEntity? {
        return genreDao.getGenreByName(name = name)
    }

    suspend fun existsName(name: String): Boolean {
        return genreDao.existsName(name = name)
    }
}