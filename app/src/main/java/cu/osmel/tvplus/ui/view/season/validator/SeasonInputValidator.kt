package cu.osmel.tvplus.ui.view.season.validator

import cu.osmel.tvplus.R
import cu.osmel.tvplus.domain.model.Season

object SeasonInputValidator {

    suspend fun validate(
        input: String
    ): Int? {
        return when {
            input.isEmpty() -> R.string.required
            else -> null
        }
    }
}
