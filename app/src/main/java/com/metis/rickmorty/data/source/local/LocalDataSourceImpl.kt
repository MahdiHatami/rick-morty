package com.metis.rickmorty.data.source.local

import com.metis.rickmorty.data.source.local.dao.DbEpisodeDao
import com.metis.rickmorty.data.source.local.entity.DbEpisode
import com.metis.rickmorty.di.scope.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
  private val episodeDao: DbEpisodeDao,
  @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : LocalDataSource {

  override suspend fun insertEpisode(entityEpisode: DbEpisode) =  withContext(ioDispatcher) {
      episodeDao.insertEpisode(entityEpisode)
    }

  override suspend fun queryAllEpisodesByPage(page: Int, pageSize: Int): List<DbEpisode> =
    withContext(ioDispatcher) {
      episodeDao.queryAllEpisodesByPage(page, pageSize)
    }
}
