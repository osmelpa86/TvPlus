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
@Entity(tableName = "channel", indices = [Index(value = ["name"], unique = true)])
data class ChannelEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_channel") val id: Long? = null,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "image") val image: String? = null
) : Serializable