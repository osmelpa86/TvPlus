package cu.osmel.tvplus.core.di

import android.content.Context
import androidx.room.Room
import cu.osmel.tvplus.data.database.TvManageDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author Osmel Pérez Alzola
 * osmelpa86@gmail.com
 */
@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    private const val TV_MANAGE_DATABASE_NAME = "tv_manage"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, TvManageDatabase::class.java, TV_MANAGE_DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideChannelDao(db: TvManageDatabase) = db.getChannelDao()

    @Singleton
    @Provides
    fun provideCountryDao(db: TvManageDatabase) = db.getCountryDao()

    @Singleton
    @Provides
    fun provideCoverDao(db: TvManageDatabase) = db.getCoverDao()

    @Singleton
    @Provides
    fun provideEpisodeDao(db: TvManageDatabase) = db.getEpisodeDao()

    @Singleton
    @Provides
    fun provideGenreDao(db: TvManageDatabase) = db.getGenresDao()

    @Singleton
    @Provides
    fun providePersonDao(db: TvManageDatabase) = db.getPersonDao()

    @Singleton
    @Provides
    fun provideSeasonDao(db: TvManageDatabase) = db.getSeasonDao()

    @Singleton
    @Provides
    fun provideTvShowCoverDao(db: TvManageDatabase) = db.getTvShowCoverDao()

    @Singleton
    @Provides
    fun provideTvShowDao(db: TvManageDatabase) = db.getTvShowDao()

    @Singleton
    @Provides
    fun provideTvShowGenreDao(db: TvManageDatabase) = db.getTvShowGenreDao()

    @Singleton
    @Provides
    fun provideTvShowChannelDao(db: TvManageDatabase) = db.getTvShowChannelDao()
}