package cu.osmel.tvplus.data.database.entities

import androidx.room.*
import java.io.Serializable
import java.util.*

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
@Entity(
    tableName = "episode",
    indices = [
        Index(value = ["id_episode"], unique = true),
        Index(value = ["id_season"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = SeasonEntity::class,
            parentColumns = ["id_season"],
            childColumns = ["id_season"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class EpisodeEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_episode")
    var idEpisode: Long? = null,
    @ColumnInfo(name = "id_season")
    var idSeason: Long? = null,
    var name: String,
    var number: Int,
    var date: Date? = null,
) : Serializable {

    override fun hashCode(): Int {
        return idEpisode.hashCode()
    }

    override fun toString(): String {
        return "Episode(idEpisode=$idEpisode, idSeason=$idSeason, name='$name', number=$number, date=$date)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EpisodeEntity

        if (idEpisode != other.idEpisode) return false
        if (name != other.name) return false
        if (number != other.number) return false

        return true
    }
}