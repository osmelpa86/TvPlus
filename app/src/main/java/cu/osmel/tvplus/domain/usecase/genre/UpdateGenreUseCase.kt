package cu.osmel.tvplus.domain.usecase.genre

import cu.osmel.tvplus.data.database.entities.GenreEntity
import cu.osmel.tvplus.data.repository.GenreRepository
import javax.inject.Inject

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
class UpdateGenreUseCase @Inject constructor(private val repository: GenreRepository) {
    suspend operator fun invoke(genre: GenreEntity) {
        return repository.update(genre = genre)
    }
}