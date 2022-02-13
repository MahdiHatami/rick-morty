package com.metis.rickmorty.data.source.remote

import com.metis.rickmorty.data.model.ApiCharacter
import com.metis.rickmorty.data.model.ApiEpisodes
import com.metis.rickmorty.data.model.ApiResult

interface RemoteDataSource {
  suspend fun fetchEpisodes(page: Int): ApiResult<ApiEpisodes>
  suspend fun fetchCharactersByIds(ids: String): ApiResult<List<ApiCharacter>>
  suspend fun fetchCharacterDetails(id: Int) : ApiResult<ApiCharacter>
}
