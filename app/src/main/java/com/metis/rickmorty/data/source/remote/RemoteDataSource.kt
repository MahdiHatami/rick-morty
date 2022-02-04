package com.metis.rickmorty.data.source.remote

import com.metis.rickmorty.data.model.ApiEpisodes
import com.metis.rickmorty.data.model.ApiResult

interface RemoteDataSource {
  suspend fun fetchEpisodes(page: Int): ApiResult<ApiEpisodes>
}
