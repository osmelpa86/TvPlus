package cu.osmel.tvplus.data.repository

import android.database.SQLException
import cu.osmel.tvplus.data.database.dao.CoverDao
import cu.osmel.tvplus.data.database.entities.CoverEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.Throws

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
class CoverRepository @Inject constructor(private val coverDao: CoverDao) {

    fun getAll(): Flow<List<CoverEntity>> {
        return coverDao.getAll()
    }

    @Throws(SQLException::class)
    suspend fun insert(cover: CoverEntity): Long {
        return coverDao.insert(cover)
    }

    suspend fun insertAll(list: List<CoverEntity>) {
        coverDao.insertAll(list)
    }

    suspend fun update(cover: CoverEntity) {
        coverDao.update(cover)
    }

    suspend fun delete(cover: CoverEntity) {
        coverDao.delete(cover)
    }

    fun delete(list: List<CoverEntity>) {
        coverDao.delete(list)
    }


    fun get(id: Long): CoverEntity? {
        return coverDao.get(id = id)
    }
}