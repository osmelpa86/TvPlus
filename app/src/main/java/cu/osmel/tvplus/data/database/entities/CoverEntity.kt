package cu.osmel.tvplus.data.database.entities

import androidx.room.*
import java.io.Serializable

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
@Entity(
    tableName = "cover", indices = [Index(value = ["id_cover"], unique = true)],
)
data class CoverEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_cover") val id: Long? = null,
    @ColumnInfo(name = "cover") val cover: String? = null,
) : Serializable