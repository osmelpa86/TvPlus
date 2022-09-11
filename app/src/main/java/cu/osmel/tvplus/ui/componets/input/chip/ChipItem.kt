package cu.osmel.tvplus.ui.componets.input.chip

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Osmel Pérez Alzola(osmelpa86@gmail.com) on 7/6/22
 */
@Parcelize
data class ChipItem(
    val id: Long? = null,
    val text: String? = null,
) : Parcelable