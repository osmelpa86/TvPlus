package cu.osmel.tvplus.ui.componets.input.exposeddropdownmenu

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.parcelize.Parcelize

/**
 * Created by Osmel Pérez Alzola(osmelpa86@gmail.com) on 7/6/22
 */
@Parcelize
data class ExposedDropdownItem(
    val id: Long? = null,
    val text: String? = null,
    val image: Bitmap? = null,
) : Parcelable