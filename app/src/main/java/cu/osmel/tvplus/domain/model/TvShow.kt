package cu.osmel.tvplus.domain.model

import android.os.Parcelable
import cu.osmel.tvplus.data.database.entities.TvShowEntity
import kotlinx.parcelize.Parcelize

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */

@Parcelize
data class TvShow(
    val serial: TvShowEntity,
    val country: Country,
    val genreList: List<Genre>,
    val coverList: List<Cover>,
    val channelList: List<Channel>,
    val seasonList: List<Season>
) : Parcelable