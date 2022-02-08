package com.metis.rickmorty.di.module

import android.content.Context
import androidx.room.Room
import com.metis.rickmorty.data.source.local.RMDatabase
import com.metis.rickmorty.data.source.local.dao.CharacterDao
import com.metis.rickmorty.data.source.local.dao.EpisodeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

  @Singleton
  @Provides
  fun provideDatabase(context: Context): RMDatabase {
    return Room.databaseBuilder(
      context,
      RMDatabase::class.java, "YRM.db"
    ).build()
  }

  @Singleton
  @Provides
  fun provideEpisodeDao(database: RMDatabase): EpisodeDao {
    return database.episodeDao
  }

  @Singleton
  @Provides
  fun provideCharacterDao(database: RMDatabase): CharacterDao {
    return database.characterDao
  }
}
