package cu.osmel.tvplus.domain.usecase.genre

import cu.osmel.tvplus.data.repository.GenreRepository
import cu.osmel.tvplus.domain.mappers.genre.toDomain
import cu.osmel.tvplus.domain.model.Genre
import javax.inject.Inject

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
class GetGenreUseCase @Inject constructor(private val repository: GenreRepository) {
    suspend operator fun invoke(id: Long): Genre? {
        return repository.get(id)?.toDomain()
    }
}