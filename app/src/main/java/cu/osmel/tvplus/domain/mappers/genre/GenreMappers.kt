package cu.osmel.tvplus.domain.mappers.genre

import cu.osmel.tvplus.data.database.entities.GenreEntity
import cu.osmel.tvplus.domain.model.Genre
import cu.osmel.tvplus.ui.componets.input.chip.ChipItem

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */

fun GenreEntity.toDomain() = Genre(id = id, name = name)
fun Genre.toDatabase() = GenreEntity(id = id, name = name)
fun Genre.toChipItem() = ChipItem(id = id, text = name)