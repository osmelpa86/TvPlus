package cu.osmel.tvplus.domain.mappers.general

import android.graphics.Bitmap
import cu.osmel.tvplus.data.database.entities.GenreEntity
import cu.osmel.tvplus.domain.model.Channel
import cu.osmel.tvplus.domain.model.Country
import cu.osmel.tvplus.domain.model.Cover
import cu.osmel.tvplus.domain.model.Genre
import cu.osmel.tvplus.ui.componets.input.chip.ChipItem
import cu.osmel.tvplus.ui.componets.input.exposeddropdownmenu.ExposedDropdownItem
import cu.osmel.tvplus.ui.componets.slider.SliderItem

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */

fun ExposedDropdownItem.toCountry() = Country(id = id, name = text.toString(), image = image?.toString())
fun ExposedDropdownItem.toChannel() = Channel(id = id, name = text.toString(), image = image?.toString())
fun ChipItem.toGenre() = Genre(id = id, name = text.toString())
fun SliderItem.toCover() = Cover( id=id, cover = imageBase64)