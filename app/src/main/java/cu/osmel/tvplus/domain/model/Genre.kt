package cu.osmel.tvplus.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
@Parcelize
data class Genre(
    val id: Long?=null,
    val name: String
): Parcelable