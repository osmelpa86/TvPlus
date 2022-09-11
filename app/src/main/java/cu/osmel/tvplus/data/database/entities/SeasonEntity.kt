package cu.osmel.tvplus.data.database.entities

import androidx.room.*
import java.io.Serializable

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
@Entity(
    tableName = "season", indices = [Index(value = ["id_season"], unique = true),
        Index(value = ["id_tv_show"])],
    foreignKeys = [
        ForeignKey(
            entity = TvShowEntity::class,
            parentColumns = ["id_tv_show"],
            childColumns = ["id_tv_show"],
            onDelete = ForeignKey.RESTRICT,
            onUpdate = ForeignKey.CASCADE
        )]
)
data class SeasonEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_season") val id: Long? = null,
    @ColumnInfo(name = "id_tv_show") val idTvShow: Long? = null,
    @ColumnInfo(name = "number") val number: Int,
    @ColumnInfo(name = "total_chapters") val totalChapters: Int,
    @ColumnInfo(name = "status") val status: String,
    @ColumnInfo(name = "year") val year: String
) : Serializable