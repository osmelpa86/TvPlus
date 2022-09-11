package cu.osmel.tvplus.domain.usecase.genre

import android.database.SQLException
import cu.osmel.tvplus.data.database.entities.GenreEntity
import cu.osmel.tvplus.data.repository.GenreRepository
import javax.inject.Inject
import kotlin.Throws

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
class InsertAllGenreUseCase @Inject constructor(private val repository: GenreRepository) {
    @Throws(SQLException::class)
    suspend operator fun invoke(list: List<GenreEntity>) {
        return repository.insertAll(list = list)
    }
}