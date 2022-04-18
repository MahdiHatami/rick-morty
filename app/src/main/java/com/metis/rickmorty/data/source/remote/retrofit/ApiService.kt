package com.metis.rickmorty.data.source.remote.retrofit

import com.metis.rickmorty.data.model.ApiCharacter
import com.metis.rickmorty.data.model.ApiEpisodes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    companion object {
        private const val CHARACTER_ENDPOINT: String = "character/"
        private const val EPISODE_ENDPOINT: String = "episode/"

        private const val PARAM_KEY_PAGE: String = "page"
        private const val PATH_KEY_CHARACTER_IDS: String = "characterIds"
        private const val PATH_KEY_CHARACTER_ID: String = "characterId"
    }

    /**
     * This function gets the [Episodes list]
     */
    @GET(value = EPISODE_ENDPOINT)
    suspend fun fetchEpisodes(
        @Query(value = PARAM_KEY_PAGE)
        page: Int,
    ): Response<ApiEpisodes>

    /**
     * This function gets the [characters list by given ids]
     */
    @GET(value = "$CHARACTER_ENDPOINT{$PATH_KEY_CHARACTER_IDS}")
    suspend fun fetchCharactersByIds(
        @Path(value = PATH_KEY_CHARACTER_IDS)
        characterIds: String,
    ): Response<List<ApiCharacter>>

    /**
     * This function gets the [character details by given id]
     */
    @GET(value = "$CHARACTER_ENDPOINT{$PATH_KEY_CHARACTER_ID}")
    suspend fun fetchCharacterDetails(
        @Path(value = PATH_KEY_CHARACTER_ID)
        characterId: Int,
    ): Response<ApiCharacter>
}
