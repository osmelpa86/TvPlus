package cu.osmel.tvplus.domain.mappers.season

import cu.osmel.tvplus.data.database.entities.SeasonEntity
import cu.osmel.tvplus.domain.model.Season

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */

fun SeasonEntity.toDomain() = Season(
    id = id,
    idTvShow = idTvShow,
    number = number,
    totalChapters = totalChapters,
    status = status,
    year = year
)

fun Season.toDatabase() = SeasonEntity(
    id = id,
    idTvShow = idTvShow,
    number = number,
    totalChapters = totalChapters,
    status = status,
    year = year
)