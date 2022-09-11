package cu.osmel.tvplus.data.database.pojo

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import cu.osmel.tvplus.data.database.entities.*

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
data class TvShowPojo(
    @Embedded val serial: TvShowEntity,
    @Relation(parentColumn = "id_country", entityColumn = "id_country")
    val country: CountryEntity,
    @Relation(
        parentColumn = "id_tv_show",
        entity = GenreEntity::class,
        entityColumn = "id_genre",
        associateBy = Junction(
            value = TvShowGenreEntity::class,
            parentColumn = "id_tv_show",
            entityColumn = "id_genre"
        )
    )
    val genreList: List<GenreEntity>,
    @Relation(
        parentColumn = "id_tv_show",
        entity = CoverEntity::class,
        entityColumn = "id_cover",
        associateBy = Junction(
            value = TvShowCoverEntity::class,
            parentColumn = "id_tv_show",
            entityColumn = "id_cover"
        )
    )
    val coverList: List<CoverEntity>,
    @Relation(
        parentColumn = "id_tv_show",
        entity = ChannelEntity::class,
        entityColumn = "id_channel",
        associateBy = Junction(
            value = TvShowChannelEntity::class,
            parentColumn = "id_tv_show",
            entityColumn = "id_channel"
        )
    )
    val channelList: List<ChannelEntity>,
    @Relation(parentColumn = "id_tv_show", entityColumn = "id_tv_show")
    var seasonList: List<SeasonEntity> = emptyList(),
)