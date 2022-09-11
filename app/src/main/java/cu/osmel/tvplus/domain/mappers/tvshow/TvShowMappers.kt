package cu.osmel.tvplus.domain.mappers.tvshow

import cu.osmel.tvplus.data.database.entities.*
import cu.osmel.tvplus.data.database.pojo.TvShowPojo
import cu.osmel.tvplus.domain.mappers.channel.toDomain
import cu.osmel.tvplus.domain.mappers.country.toDomain
import cu.osmel.tvplus.domain.mappers.cover.toDomain
import cu.osmel.tvplus.domain.mappers.genre.toDomain
import cu.osmel.tvplus.domain.mappers.season.toDomain
import cu.osmel.tvplus.domain.model.TvShow

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
fun TvShowPojo.toDomain() = TvShow(
    serial = serial,
    country = country.toDomain(),
    genreList = genreList.map { it.toDomain() },
    coverList = coverList.map { it.toDomain() },
    channelList = channelList.map { it.toDomain() },
    seasonList = seasonList.map { it.toDomain() }
)

fun TvShow.toDatabase() = TvShowEntity(
    id = serial.id,
    title = serial.title,
    year = serial.year,
    idCountry = serial.idCountry,
    duration = serial.duration,
    type = serial.type,
    synopsis = serial.synopsis,
    status = serial.status
)