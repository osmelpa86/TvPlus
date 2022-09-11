package cu.osmel.tvplus.domain.mappers.country

import android.graphics.Bitmap
import cu.osmel.tvplus.data.database.entities.CountryEntity
import cu.osmel.tvplus.domain.model.Country
import cu.osmel.tvplus.ui.componets.input.exposeddropdownmenu.ExposedDropdownItem
import cu.osmel.tvplus.ui.componets.util.decodeImageBase64

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */

fun CountryEntity.toDomain() = Country(id = id, name = name, image = image)
fun Country.toDatabase() = CountryEntity(id = id, name = name, image = image)
fun Country.toExposedDropdownItem() = ExposedDropdownItem(
    id = id,
    text = name,
    image = image?.let { decodeImageBase64(it) }
)