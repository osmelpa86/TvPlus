package cu.osmel.tvplus.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import cu.osmel.tvplus.data.database.converter.Converters
import cu.osmel.tvplus.data.database.dao.*
import cu.osmel.tvplus.data.database.entities.*

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
@Database(
    entities = [
        ChannelEntity::class,
        CountryEntity::class,
        CoverEntity::class,
        EpisodeEntity::class,
        GenreEntity::class,
        PersonEntity::class,
        SeasonEntity::class,
        TvShowCoverEntity::class,
        TvShowEntity::class,
        TvShowGenreEntity::class,
        TvShowChannelEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class TvManageDatabase : RoomDatabase() {
    abstract fun getChannelDao(): ChannelDao
    abstract fun getCountryDao(): CountryDao
    abstract fun getCoverDao(): CoverDao
    abstract fun getEpisodeDao(): EpisodeDao
    abstract fun getGenresDao(): GenreDao
    abstract fun getPersonDao(): PersonDao
    abstract fun getSeasonDao(): SeasonDao
    abstract fun getTvShowCoverDao(): TvShowCoverDao
    abstract fun getTvShowDao(): TvShowDao
    abstract fun getTvShowGenreDao(): TvShowGenreDao
    abstract fun getTvShowChannelDao(): TvShowChannelDao
    companion object {
        @Volatile
        private var INSTANCE: TvManageDatabase? = null

        fun getDatabase(context: Context): TvManageDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TvManageDatabase::class.java,
                    "tv_manage"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}