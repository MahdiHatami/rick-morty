package com.metis.rickmorty.data.source.local

import com.metis.rickmorty.data.source.local.entity.DbEpisode

interface LocalDataSource {

  suspend fun insertEpisode(entityEpisode: DbEpisode)

  suspend fun queryAllEpisodesByPage(page: Int, pageSize: Int): List<DbEpisode>
}
