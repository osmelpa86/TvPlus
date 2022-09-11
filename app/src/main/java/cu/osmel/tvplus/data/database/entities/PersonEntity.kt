package cu.osmel.tvplus.data.database.entities

import androidx.room.*
import java.io.Serializable

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
@Entity(
    tableName = "person", indices = [Index(value = ["id_person"], unique = true),
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
data class PersonEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_person") val id: Long? = null,
    @ColumnInfo(name = "name") val title: String,
    @ColumnInfo(name = "image") val image: String? = null,
    @ColumnInfo(name = "sex") val sex: String,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "id_country") val idCountry: Long

) : Serializable