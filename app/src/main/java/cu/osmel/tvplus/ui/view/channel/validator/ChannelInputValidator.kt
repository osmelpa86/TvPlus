package cu.osmel.tvplus.ui.view.channel.validator

import cu.osmel.tvplus.R
import cu.osmel.tvplus.domain.model.Channel
import cu.osmel.tvplus.domain.usecase.channel.GetChannelByNameUseCase

object ChannelInputValidator {

    suspend fun validate(
        input: String,
        getChannelByNameUseCase: GetChannelByNameUseCase,
        edit: Boolean,
        channelModel: Channel?
    ): Int? {
        return when {
            input.isEmpty() -> R.string.required
            input.isNotEmpty() -> {
                var result: Int? = null
                val channel: Channel? = getChannelByNameUseCase(input)
                when (edit) {
                    false -> {
                        if (channel !== null) result = R.string.exist_name
                    }
                    true -> {
                        if (channel != channelModel) {
                            if (channel !== null) result = R.string.exist_name
                        }
                    }
                }
                return result
            }
            else -> null
        }
    }
}
