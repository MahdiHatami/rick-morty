package com.metis.rickmorty.data.source.local

import com.metis.rickmorty.data.source.local.entity.DbCharacter
import com.metis.rickmorty.data.source.local.entity.DbEpisode

interface LocalDataSource {

  suspend fun insertEpisode(entityEpisode: DbEpisode)

  suspend fun queryAllEpisodesByPage(page: Int, pageSize: Int): List<DbEpisode>

  suspend fun insertCharacter(entityCharacter: DbCharacter)

  suspend fun queryCharacterByIds(ids: List<Int>): List<DbCharacter>

  suspend fun queryCharacterById(id: Int): DbCharacter?
}
