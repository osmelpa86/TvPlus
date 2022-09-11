package cu.osmel.tvplus.data.database.entities

import androidx.room.*
import java.io.Serializable

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
@Entity(
    tableName = "cover_show",
    indices = [
        Index(value = ["id_tv_show_cover"], unique = true),
        Index(value = ["id_cover"]),
        Index(value = ["id_tv_show"])],
    foreignKeys = [
        ForeignKey(
            entity = CoverEntity::class,
            parentColumns = ["id_cover"],
            childColumns = ["id_cover"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = TvShowEntity::class,
            parentColumns = ["id_tv_show"],
            childColumns = ["id_tv_show"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TvShowCoverEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_tv_show_cover") val id: Long? = null,
    @ColumnInfo(name = "id_cover") val idCover: Long,
    @ColumnInfo(name = "id_tv_show") val idTvShow: Long,
) : Serializable
