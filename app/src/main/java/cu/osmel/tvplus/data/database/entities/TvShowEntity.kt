package cu.osmel.tvplus.data.database.entities

import androidx.room.*
import java.io.Serializable

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
@Entity(
    tableName = "tv_show", indices = [Index(value = ["id_tv_show"], unique = true),
        Index(value = ["id_country"])],
    foreignKeys = [
        ForeignKey(
            entity = CountryEntity::class,
            parentColumns = ["id_country"],
            childColumns = ["id_country"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )]
)
data class TvShowEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_tv_show") val id: Long? = null,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "year") val year: Int,
    @ColumnInfo(name = "id_country") val idCountry: Long? = null,
    @ColumnInfo(name = "duration") val duration: String,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "synopsis") val synopsis: String,
    @ColumnInfo(name = "status") val status: String,
) : Serializable