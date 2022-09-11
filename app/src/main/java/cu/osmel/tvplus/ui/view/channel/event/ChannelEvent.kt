package cu.osmel.tvplus.ui.view.channel.event

import cu.osmel.tvplus.domain.model.Channel

/**
 * Created by Osmel Pérez Alzola(osmelpa86@gmail.com) on 18/5/22
 */
sealed class ChannelEvent {
    data class InsertChannel(val channel: Channel) : ChannelEvent()
    data class InsertAllChannel(val list: List<Channel>) : ChannelEvent()
    data class DeleteChannel(val channel: Channel) : ChannelEvent()
    data class UpdateEvent(val channel: Channel) : ChannelEvent()
}

