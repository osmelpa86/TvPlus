package cu.osmel.tvplus.data.database.entities

import androidx.room.*
import java.io.Serializable

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
@Entity(
    tableName = "genre_show",
    indices = [
        Index(value = ["id_tv_show_genre"], unique = true),
        Index(value = ["id_genre"]),
        Index(value = ["id_tv_show"])],
    foreignKeys = [
        ForeignKey(
            entity = GenreEntity::class,
            parentColumns = ["id_genre"],
            childColumns = ["id_genre"],
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
data class TvShowGenreEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_tv_show_genre") val id: Long? = null,
    @ColumnInfo(name = "id_genre") val idGenre: Long,
    @ColumnInfo(name = "id_tv_show") val idTvShow: Long,
) : Serializable
