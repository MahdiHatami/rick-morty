package com.metis.rickmorty.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.metis.rickmorty.data.source.local.converters.DbTypeConverters
import com.metis.rickmorty.data.source.local.dao.CharacterDao
import com.metis.rickmorty.data.source.local.dao.EpisodeDao
import com.metis.rickmorty.data.source.local.entity.DbCharacter
import com.metis.rickmorty.data.source.local.entity.DbEpisode

@Database(entities = [DbEpisode::class, DbCharacter::class], version = 1, exportSchema = true)

@TypeConverters(DbTypeConverters::class)
abstract class RMDatabase : RoomDatabase() {

  abstract val episodeDao: EpisodeDao
  abstract val characterDao: CharacterDao
}
