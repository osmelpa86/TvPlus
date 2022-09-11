package cu.osmel.tvplus.data.database.converter

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
class Converters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun dateToTime(value: Date?): String? {
        return if (value != null) {
            formatDate(value)
        } else {
            null
        }
    }
}

fun formatDate(date: Date): String = SimpleDateFormat("dd/MM/yyyy").format(date)