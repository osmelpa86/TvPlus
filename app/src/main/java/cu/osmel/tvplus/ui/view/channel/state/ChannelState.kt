package cu.osmel.tvplus.ui.view.channel.state

import cu.osmel.tvplus.domain.model.Channel

/**
 * Created by Osmel Pérez Alzola(osmelpa86@gmail.com) on 18/5/22
 */
data class ChannelState(
    val listChannels: List<Channel> = emptyList()
)
