package com.metis.rickmorty.ui.episode

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.metis.rickmorty.MainCoroutineRule
import com.metis.rickmorty.data.model.ApiEpisodes
import com.metis.rickmorty.data.model.ApiResult
import com.metis.rickmorty.data.source.local.LocalDataSource
import com.metis.rickmorty.data.source.local.entity.DbEpisode
import com.metis.rickmorty.data.source.remote.RemoteDataSource
import com.metis.rickmorty.data.source.repository.Repository
import com.metis.rickmorty.data.source.repository.RepositoryImpl
import com.metis.rickmorty.domain.model.ModelCharacter
import com.metis.rickmorty.domain.model.ModelEpisode
import com.metis.rickmorty.domain.model.PageQueryResult
import com.metis.rickmorty.domain.model.QueryResult
import com.metis.rickmorty.factory.ApiFactory
import com.metis.rickmorty.factory.CharacterDataFactory
import com.metis.rickmorty.factory.EpisodeDataFactory
import com.metis.rickmorty.mapper.toModelCharacter
import com.metis.rickmorty.utils.StatusProvider
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class EpisodeListViewModelTest {

    @MockK
    lateinit var database: LocalDataSource

    @MockK
    lateinit var api: RemoteDataSource

    @MockK
    lateinit var internetStatus: StatusProvider

    lateinit var repository: Repository

    // Executes tasks in the Architecture Components in the same thread
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    // Overrides Dispatchers.Main used in Coroutines
    @get:Rule
    var coroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        repository = RepositoryImpl(
            api = api,
            db = database,
            statusProvider = internetStatus,
            ioDispatcher = coroutineRule.testDispatcher
        )
    }

    @Test
    fun `Test loadData happy path`() = runBlockingTest {
        // GIVEN
        stubStatusProviderIsOnline(isOnline = true)
        stubApiFetchEpisodes(ApiFactory.Episode.makeApiEpisodes())
        val entityEpisodes = EpisodeDataFactory.makeDbEpisodes(count = 6)
        stubEpisodeDaoQueryAllEpisodesByPage(entityEpisodes)
        stubDbInsertEpisode()

        val viewModel = EpisodeListViewModel(
            repository = FakeRepository(),
            statusProvider = internetStatus
        )

        // WHEN
        val episode = viewModel.episodes.value

        // THEN
        assertThat(episode, equalTo(entityEpisodes))
    }

    @Test
    fun `Check if loading more activate the progress`() = runBlockingTest {
        // GIVEN
        stubStatusProviderIsOnline(isOnline = false)
        val viewModel = EpisodeListViewModel(
            repository = FakeRepository(),
            statusProvider = internetStatus
        )
        // WHEN
        viewModel.loadMoreEpisodes(page = 1)

        // THEN
        assertThat(viewModel.loadingState.value, equalTo(true))
    }

    private fun stubStatusProviderIsOnline(isOnline: Boolean) {
        every { internetStatus.isOnline() } returns isOnline
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

internal class FakeRepository : Repository {
    override suspend fun getEpisodes(page: Int): PageQueryResult<List<ModelEpisode>> {
        return PageQueryResult.Successful(EpisodeDataFactory.makeEpisodes(6))
    }

    override suspend fun getCharactersByIds(characterIds: List<Int>): QueryResult<List<ModelCharacter>> {
        return QueryResult.Successful(
            mutableListOf(
                CharacterDataFactory.makeDbCharacter().toModelCharacter()
            )
        )
    }

    override suspend fun getCharacterDetails(characterId: Int): QueryResult<ModelCharacter?> {
        return QueryResult.Successful(
            CharacterDataFactory.makeDbCharacter().toModelCharacter()
        )
    }
}
