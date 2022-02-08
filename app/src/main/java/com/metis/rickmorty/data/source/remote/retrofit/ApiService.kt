package com.metis.rickmorty.data.source.remote.retrofit

import com.metis.rickmorty.data.model.ApiCharacter
import com.metis.rickmorty.data.model.ApiEpisodes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

  /**
   * This function gets the [Episodes list]
   */
  @GET("episode/")
  suspend fun fetchEpisodes(
    @Query("page") page: Int
  ): Response<ApiEpisodes>

  @GET("character/")
  suspend fun fetchCharactersByIds(
    @Path("characterIds")
    characterIds: String,
  ): Response<List<ApiCharacter>>
}
