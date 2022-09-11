package cu.osmel.tvplus.domain.usecase.tvshow

import cu.osmel.tvplus.data.database.pojo.TvShowPojo
import cu.osmel.tvplus.data.repository.TvShowRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
class GetByIdTvShowUseCase @Inject constructor(private val repository: TvShowRepository) {
    suspend operator fun invoke(id:Long):TvShowPojo? {
        return repository.get(id)
    }
}