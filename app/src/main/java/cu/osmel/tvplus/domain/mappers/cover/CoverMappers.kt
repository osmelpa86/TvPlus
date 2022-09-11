package cu.osmel.tvplus.domain.mappers.cover

import android.graphics.Bitmap
import cu.osmel.tvplus.data.database.entities.CountryEntity
import cu.osmel.tvplus.data.database.entities.CoverEntity
import cu.osmel.tvplus.domain.model.Country
import cu.osmel.tvplus.domain.model.Cover
import cu.osmel.tvplus.ui.componets.slider.SliderItem
import cu.osmel.tvplus.ui.componets.util.decodeImageBase64

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */

fun CoverEntity.toDomain() = Cover(id = id, cover = cover)
fun Cover.toDatabase() = CoverEntity(id = id, cover = cover)
fun Cover.toSliderItem() = SliderItem(
    id = id, text = null,
    image = if (cover?.isNotEmpty() === true) decodeImageBase64(cover) else null,
    imageBase64 = cover,
)