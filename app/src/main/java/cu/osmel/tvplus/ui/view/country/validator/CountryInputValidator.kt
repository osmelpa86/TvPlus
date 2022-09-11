package cu.osmel.tvplus.ui.view.country.validator

import cu.osmel.tvplus.R
import cu.osmel.tvplus.domain.model.Country
import cu.osmel.tvplus.domain.usecase.country.GetCountryByNameUseCase

object CountryInputValidator {

    suspend fun validate(
        input: String,
        getCountryByNameUseCase: GetCountryByNameUseCase,
        edit: Boolean,
        countryModel: Country?
    ): Int? {
        return when {
            input.isEmpty() -> R.string.required
            input.isNotEmpty() -> {
                var result: Int? = null
                val country: Country? = getCountryByNameUseCase(input)
                when (edit) {
                    false -> {
                        if (country !== null) result = R.string.exist_name
                    }
                    true -> {
                        if (country != countryModel) {
                            if (country !== null) result = R.string.exist_name
                        }
                    }
                }
                return result
            }
            else -> null
        }
    }
}
