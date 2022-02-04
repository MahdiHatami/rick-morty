package com.metis.rickmorty.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.metis.rickmorty.data.source.local.entity.DbEpisode

@Dao
interface DbEpisodeDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertEpisode(vararg entityEpisode: DbEpisode)

  @Query("SELECT * FROM episodes")
  suspend fun queryAllEpisodes(): List<DbEpisode>

  @Query("SELECT * FROM episodes LIMIT :pageSize OFFSET (:page - 1) * :pageSize")
  suspend fun queryAllEpisodesByPage(page: Int, pageSize: Int): List<DbEpisode>
}
