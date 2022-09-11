package cu.osmel.tvplus.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
@Entity(
    tableName = "genre",
    indices = [Index(
        value = ["name"],
        unique = true
    )]
)
data class GenreEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_genre")
    val id: Long? = null,
    @ColumnInfo(name = "name") val name: String,
) : Serializable
