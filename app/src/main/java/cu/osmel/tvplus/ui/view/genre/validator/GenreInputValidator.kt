package cu.osmel.tvplus.ui.view.genre.validator

import cu.osmel.tvplus.R
import cu.osmel.tvplus.domain.model.Genre
import cu.osmel.tvplus.domain.usecase.genre.GetGenreByNameUseCase

object GenreInputValidator {

    suspend fun validate(
        input: String,
        getGenreByNameUseCase: GetGenreByNameUseCase,
        edit: Boolean,
        genreModel: Genre?
    ): Int? {
        return when {
            input.isEmpty() -> R.string.required
            input.isNotEmpty() -> {
                var result: Int? = null
                val genre: Genre? = getGenreByNameUseCase(input)
                when (edit) {
                    false -> {
                        if (genre !== null) result = R.string.exist_name
                    }
                    true -> {
                        if (genre != genreModel) {
                            if (genre !== null) result = R.string.exist_name
                        }
                    }
                }
                return result
            }
            else -> null
        }
    }
}
