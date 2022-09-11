package cu.osmel.tvplus.ui.componets.input

import cu.osmel.tvplus.R

object InputValidator {

    fun getRequiredErrorIdOrNull(input: String): Int? {
        return when {
            input.isEmpty() -> R.string.required
            input.isNotEmpty() -> R.string.required
            else -> null
        }
    }

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

}
