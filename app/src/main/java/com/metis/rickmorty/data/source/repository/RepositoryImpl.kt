package com.metis.rickmorty.data.source.repository

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import com.metis.rickmorty.data.model.ApiEpisode
import com.metis.rickmorty.data.model.ApiResult
import com.metis.rickmorty.data.source.local.LocalDataSource
import com.metis.rickmorty.data.source.local.entity.DbEpisode
import com.metis.rickmorty.data.source.remote.RemoteDataSource
import com.metis.rickmorty.domain.model.ModelEpisode
import com.metis.rickmorty.domain.model.PageQueryResult
import com.metis.rickmorty.mapper.toDbEpisode
import com.metis.rickmorty.mapper.toModelEpisode
import com.metis.rickmorty.utils.StatusProvider
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
  private val api: RemoteDataSource,
  private val db: LocalDataSource,
  private val statusProvider: StatusProvider,
) : Repository {
  override suspend fun getEpisodes(page: Int): PageQueryResult<List<ModelEpisode>> {
    if (statusProvider.isOnline()) {
      val result = fetchNetworkEpisodes(page)
      if (result is PageQueryResult.Successful) cacheNetworkEpisodes(result.data)
    }
    return queryDbEpisodes(page, PAGE_SIZE)
  }

  private suspend fun fetchNetworkEpisodes(page: Int): PageQueryResult<List<ApiEpisode>> =
    when (val result = api.fetchEpisodes(page)) {
      is ApiResult.Success -> PageQueryResult.Successful(result.data.results)
      is ApiResult.Error -> PageQueryResult.Error
    }

  private suspend fun cacheNetworkEpisodes(episodes: List<ApiEpisode>) {
    episodes.map(ApiEpisode::toDbEpisode)
      .forEach { db.insertEpisode(it) }
  }

  private suspend fun queryDbEpisodes(
    page: Int,
    pageSize: Int,
  ): PageQueryResult<List<ModelEpisode>> {
    val dbEntityEpisodes: List<DbEpisode> = db.queryAllEpisodesByPage(page, pageSize)
    val episodes: List<ModelEpisode> = dbEntityEpisodes.map(DbEpisode::toModelEpisode)

    if (episodes.isEmpty()) return PageQueryResult.EndOfList

    return PageQueryResult.Successful(episodes)
  }
}
