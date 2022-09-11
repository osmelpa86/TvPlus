package cu.osmel.tvplus.domain.usecase.genre

import cu.osmel.tvplus.data.database.entities.GenreEntity
import cu.osmel.tvplus.data.repository.GenreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
class GetAllGenreUseCase @Inject constructor(private val repository: GenreRepository) {
    operator fun invoke(): Flow<List<GenreEntity>> {
        return repository.getAll()
    }
}