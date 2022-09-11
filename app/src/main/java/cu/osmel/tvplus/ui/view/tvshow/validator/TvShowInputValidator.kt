package cu.osmel.tvplus.ui.view.tvshow.validator

import cu.osmel.tvplus.R
import cu.osmel.tvplus.domain.model.Channel
import cu.osmel.tvplus.domain.usecase.channel.GetChannelByNameUseCase

object TvShowInputValidator {
    fun validateRequired(
        input: String,
    ): Int? {
        return when {
            input.isEmpty() -> R.string.required
            else -> null
        }
    }
}


//object InputValidator {
//
//    fun getNameErrorIdOrNull(input: String): Int? {
//        return when {
//            input.length < 2 -> R.string.name_too_short
//            //etc..
//            else -> null
//        }
//    }
//
//    fun getCardNumberErrorIdOrNull(input: String): Int? {
//        return when {
//            input.length < 16 -> R.string.card_number_too_short
//            //etc..
//            else -> null
//        }
//    }
//
//}