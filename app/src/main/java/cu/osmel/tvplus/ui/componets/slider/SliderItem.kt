package cu.osmel.tvplus.ui.componets.slider

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Osmel Pérez Alzola(osmelpa86@gmail.com) on 11/6/22
 */
@Parcelize
data class SliderItem(
    val id: Long? = null,
    val text: String? = null,
    val image: Bitmap? = null,
    val imageBase64: String? = null,
) : Parcelable