package cu.osmel.tvplus.domain.usecase.tvshow

import cu.osmel.tvplus.data.database.entities.GenreEntity
import cu.osmel.tvplus.data.database.entities.TvShowEntity
import cu.osmel.tvplus.data.repository.GenreRepository
import cu.osmel.tvplus.data.repository.TvShowRepository
import javax.inject.Inject

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
class DeleteTvShowUseCase @Inject constructor(private val repository: TvShowRepository) {
    suspend operator fun invoke(tvShow: TvShowEntity) {
        return repository.delete(tvShow)
    }
}