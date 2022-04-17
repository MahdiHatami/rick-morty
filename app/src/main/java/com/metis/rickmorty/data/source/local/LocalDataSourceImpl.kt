package com.metis.rickmorty.data.source.local

import com.metis.rickmorty.data.source.local.dao.CharacterDao
import com.metis.rickmorty.data.source.local.dao.EpisodeDao
import com.metis.rickmorty.data.source.local.entity.DbCharacter
import com.metis.rickmorty.data.source.local.entity.DbEpisode
import com.metis.rickmorty.di.scope.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
  private val episodeDao: EpisodeDao,
  private val characterDao: CharacterDao,
  @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : LocalDataSource {

  override suspend fun insertEpisode(entityEpisode: DbEpisode) = withContext(ioDispatcher) {
    episodeDao.insertEpisode(entityEpisode)
  }

  override suspend fun queryAllEpisodesByPage(page: Int, pageSize: Int): List<DbEpisode> =
    withContext(ioDispatcher) {
      episodeDao.queryAllEpisodesByPage(page, pageSize)
    }

  override suspend fun queryAllEpisodes(): List<DbEpisode> =
    withContext(ioDispatcher) {
      episodeDao.queryAllEpisodes()
    }

  override suspend fun insertCharacter(entityCharacter: DbCharacter) = withContext(ioDispatcher) {
    withContext(ioDispatcher) {
      characterDao.insertCharacter(entityCharacter)
    }
  }

  override suspend fun queryCharacterByIds(characterIds: List<Int>): List<DbCharacter> =
    withContext(ioDispatcher) {
      characterDao.queryCharactersByIds(characterIds)
    }

  override suspend fun queryCharacterById(id: Int): DbCharacter? =
    withContext(ioDispatcher) {
      characterDao.queryCharacter(id)
    }
}
