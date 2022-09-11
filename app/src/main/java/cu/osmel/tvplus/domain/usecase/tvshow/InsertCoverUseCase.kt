package cu.osmel.tvplus.domain.usecase.tvshow

import android.database.SQLException
import cu.osmel.tvplus.data.database.entities.CoverEntity
import cu.osmel.tvplus.data.repository.CoverRepository
import javax.inject.Inject
import kotlin.Throws

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
class InsertCoverUseCase @Inject constructor(private val repository: CoverRepository) {
    @Throws(SQLException::class)
    suspend operator fun invoke(cover: CoverEntity): Long {
      return repository.insert(cover = cover)
    }
}