package com.metis.rickmorty.data.source.repository

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import com.metis.rickmorty.data.model.ApiCharacter
import com.metis.rickmorty.data.model.ApiEpisode
import com.metis.rickmorty.data.model.ApiResult
import com.metis.rickmorty.data.source.local.LocalDataSource
import com.metis.rickmorty.data.source.local.entity.DbCharacter
import com.metis.rickmorty.data.source.local.entity.DbEpisode
import com.metis.rickmorty.data.source.remote.RemoteDataSource
import com.metis.rickmorty.domain.model.ModelCharacter
import com.metis.rickmorty.domain.model.ModelEpisode
import com.metis.rickmorty.domain.model.PageQueryResult
import com.metis.rickmorty.domain.model.QueryResult
import com.metis.rickmorty.mapper.toDbCharacter
import com.metis.rickmorty.mapper.toDbEpisode
import com.metis.rickmorty.mapper.toModelCharacter
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
      val result = fetchEpisodes(page)
      if (result is PageQueryResult.Successful) cacheEpisodes(result.data)
    }
    return queryDbEpisodes(page, PAGE_SIZE)
  }

  override suspend fun getCharactersByIds(characterIds: List<Int>): QueryResult<List<ModelCharacter>> {
    if (statusProvider.isOnline()) {
      val result = fetchCharactersByIds(characterIds)
      if (result is QueryResult.Successful) cacheCharacters(result.data)
    }
    return queryDbCharacters(characterIds)
  }

  override suspend fun getCharacterDetails(characterId: Int): QueryResult<ModelCharacter?> {
    return queryDbCharacterById(characterId)
  }

  private suspend fun queryDbCharacterById(characterId: Int): QueryResult<ModelCharacter?>{
    val entity = db.queryCharacterById(characterId)
    val modelCharacter = entity?.toModelCharacter()
    return QueryResult.Successful(modelCharacter)
  }

  private suspend fun queryDbCharacters(characterIds: List<Int>): QueryResult<List<ModelCharacter>> {
    val entityList = db.queryCharacterByIds(characterIds)
    val modelCharacters = entityList.map(DbCharacter::toModelCharacter)
    if (modelCharacters.isEmpty()) QueryResult.NoCache

    return QueryResult.Successful(modelCharacters)
  }

  private suspend fun cacheCharacters(characters: List<ApiCharacter>) {
    characters.map(ApiCharacter::toDbCharacter)
      .forEach { db.insertCharacter(it) }
  }

  private suspend fun fetchCharactersByIds(characterIds: List<Int>): QueryResult<List<ApiCharacter>> =
    when (val result = api.fetchCharactersByIds(characterIds.joinToString(","))) {
      is ApiResult.Success -> QueryResult.Successful(result.data)
      is ApiResult.Error.ServerError,
      is ApiResult.Error.UnknownError
      -> QueryResult.Error
    }

  private suspend fun fetchEpisodes(page: Int): PageQueryResult<List<ApiEpisode>> =
    when (val result = api.fetchEpisodes(page)) {
      is ApiResult.Success -> PageQueryResult.Successful(result.data.results)
      is ApiResult.Error -> PageQueryResult.Error
    }

  private suspend fun cacheEpisodes(episodes: List<ApiEpisode>) {
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
