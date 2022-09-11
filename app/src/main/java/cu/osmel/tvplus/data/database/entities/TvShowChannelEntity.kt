package cu.osmel.tvplus.data.database.entities

import androidx.room.*
import java.io.Serializable

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
@Entity(
    tableName = "channel_show",
    indices = [
        Index(value = ["id_tv_show_channel"], unique = true),
        Index(value = ["id_channel"]),
        Index(value = ["id_tv_show"])],
    foreignKeys = [
        ForeignKey(
            entity = ChannelEntity::class,
            parentColumns = ["id_channel"],
            childColumns = ["id_channel"],
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
data class TvShowChannelEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_tv_show_channel") val id: Long? = null,
    @ColumnInfo(name = "id_channel") val idChannel: Long,
    @ColumnInfo(name = "id_tv_show") val idTvShow: Long,
) : Serializable
