package com.metis.rickmorty.data.source.repository

import com.metis.rickmorty.factory.ApiFactory
import com.metis.rickmorty.data.model.ApiEpisodes
import com.metis.rickmorty.data.model.ApiResult
import com.metis.rickmorty.data.source.local.entity.DbEpisode
import com.metis.rickmorty.factory.EpisodeDataFactory
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.runs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RepositoryGetEpisodesTest : RepositoryTest() {

  @Test
  fun `getEpisodes calls api and db when device isOnline`() = runBlockingTest {
    // GIVEN
    stubStatusProviderIsOnline(isOnline = true)
    stubApiFetchEpisodes(ApiFactory.Episode.makeApiEpisodes())
    val entityEpisodes = EpisodeDataFactory.makeDbEpisodes(count = 6)
    stubEpisodeDaoQueryAllEpisodesByPage(entityEpisodes)
    stubDbInsertEpisode()

    // WHEN
    repository.getEpisodes(page = 1)

    // THEN
    coVerify(exactly = 1) { api.fetchEpisodes(page = 1) }
    coVerify(exactly = 1) { database.insertEpisode(entityEpisode = any()) }
    coVerify(exactly = 1) { database.queryAllEpisodesByPage(page = any(), pageSize = any()) }
  }

  @Test
  fun `getEpisodes call on db when device offline`() = runBlockingTest {
    // GIVEN
    stubStatusProviderIsOnline(isOnline = false)
    val entityEpisodes = EpisodeDataFactory.makeDbEpisodes(count = 6)
    stubEpisodeDaoQueryAllEpisodesByPage(entityEpisodes)

    // WHEN
    repository.getEpisodes(page = 1)

    // THEN
    coVerify (exactly = 1) { database.queryAllEpisodesByPage(page = any(), pageSize = any()) }
  }

  private fun stubApiFetchEpisodes(episodes: ApiEpisodes) {
    coEvery { api.fetchEpisodes(page = any()) } returns ApiResult.Success(episodes)
  }

  private fun stubDbInsertEpisode() {
    coEvery { database.insertEpisode(any()) } just runs
  }

  private fun stubEpisodeDaoQueryAllEpisodesByPage(entityEpisodes: List<DbEpisode>) {
    coEvery {
      database.queryAllEpisodesByPage(
        page = any(),
        pageSize = any()
      )
    } returns entityEpisodes
  }
}