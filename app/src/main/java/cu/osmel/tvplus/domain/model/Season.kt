package cu.osmel.tvplus.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
@Parcelize
data class Season(
    val id: Long? = null,
    var idTvShow: Long? = null,
    val number: Int,
    val totalChapters: Int,
    val status: String,
    val year: String,
    var select: Boolean = false
) : Parcelable