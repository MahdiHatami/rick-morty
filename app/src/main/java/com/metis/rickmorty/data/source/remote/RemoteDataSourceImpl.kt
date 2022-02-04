package com.metis.rickmorty.data.source.remote

import com.metis.rickmorty.data.mapper.ApiResultMapper.toApiResult
import com.metis.rickmorty.data.model.ApiEpisodes
import com.metis.rickmorty.data.model.ApiResult
import com.metis.rickmorty.data.source.remote.retrofit.ApiService
import com.metis.rickmorty.di.scope.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
  private val api: ApiService
) : RemoteDataSource {

  override suspend fun fetchEpisodes(page: Int): ApiResult<ApiEpisodes> =
    toApiResult {
      api.fetchEpisodes(page)
    }
}
