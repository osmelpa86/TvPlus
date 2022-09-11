package cu.osmel.tvplus.domain.mappers.channel

import cu.osmel.tvplus.data.database.entities.ChannelEntity
import cu.osmel.tvplus.domain.model.Channel

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */

fun ChannelEntity.toDomain() = Channel(id = id, name = name, image = image)
fun Channel.toDatabase() = ChannelEntity(id = id, name = name, image = image)