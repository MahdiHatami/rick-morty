package com.metis.rickmorty.data.source.repository

import com.metis.rickmorty.domain.model.ModelCharacter
import com.metis.rickmorty.domain.model.ModelEpisode
import com.metis.rickmorty.domain.model.PageQueryResult
import com.metis.rickmorty.domain.model.QueryResult

interface Repository {
    suspend fun getEpisodes(page: Int): PageQueryResult<List<ModelEpisode>>
    suspend fun getCharactersByIds(characterIds: List<Int>): QueryResult<List<ModelCharacter>>
    suspend fun getCharacterDetails(characterId: Int): QueryResult<ModelCharacter?>
}
