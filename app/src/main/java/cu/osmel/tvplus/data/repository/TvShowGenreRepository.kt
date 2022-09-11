package cu.osmel.tvplus.data.repository

import android.database.SQLException
import cu.osmel.tvplus.data.database.dao.TvShowGenreDao
import cu.osmel.tvplus.data.database.entities.TvShowGenreEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.Throws

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
class TvShowGenreRepository @Inject constructor(private val tvShowGenreDao: TvShowGenreDao) {

    fun getAll(): Flow<List<TvShowGenreEntity>> {
        return tvShowGenreDao.getAll()
    }

    @Throws(SQLException::class)
    suspend fun insert(tvShowGenre: TvShowGenreEntity): Long {
        return tvShowGenreDao.insert(tvShowGenre)
    }

    suspend fun insertAll(list: List<TvShowGenreEntity>) {
        tvShowGenreDao.insertAll(list)
    }

    suspend fun update(tvShowGenre: TvShowGenreEntity) {
        tvShowGenreDao.update(tvShowGenre)
    }

    suspend fun delete(tvShowGenre: TvShowGenreEntity) {
        tvShowGenreDao.delete(tvShowGenre)
    }

    suspend fun delete(list: List<TvShowGenreEntity>) {
        tvShowGenreDao.delete(list)
    }

    suspend fun get(id: Long): TvShowGenreEntity? {
        return tvShowGenreDao.get(id = id)
    }
}