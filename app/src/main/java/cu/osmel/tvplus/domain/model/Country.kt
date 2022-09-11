package cu.osmel.tvplus.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
@Parcelize
data class Country(
    val id: Long? = null,
    val name: String,
    val image: String? = null
) : Parcelable