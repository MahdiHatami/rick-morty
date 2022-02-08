package com.metis.rickmorty.data.source.remote

import com.metis.rickmorty.data.mapper.ApiResultMapper.toApiResult
import com.metis.rickmorty.data.model.ApiCharacter
import com.metis.rickmorty.data.model.ApiEpisodes
import com.metis.rickmorty.data.model.ApiResult
import com.metis.rickmorty.data.source.remote.retrofit.ApiService
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
  private val api: ApiService
) : RemoteDataSource {

  override suspend fun fetchEpisodes(page: Int): ApiResult<ApiEpisodes> =
    toApiResult {
      api.fetchEpisodes(page)
    }

  override suspend fun fetchCharactersByIds(ids: String): ApiResult<List<ApiCharacter>> =
    toApiResult {
      api.fetchCharactersByIds(ids)
    }
}
