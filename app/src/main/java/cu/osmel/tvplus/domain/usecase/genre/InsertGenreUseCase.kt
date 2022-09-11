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
class InsertGenreUseCase @Inject constructor(private val repository: GenreRepository) {
    @Throws(SQLException::class)
    suspend operator fun invoke(genre: GenreEntity):Long {
        return repository.insert(genre = genre)
    }
}