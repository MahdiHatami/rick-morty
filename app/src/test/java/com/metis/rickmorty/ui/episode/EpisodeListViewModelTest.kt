package com.metis.rickmorty.ui.episode

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.metis.rickmorty.MainCoroutineRule
import com.metis.rickmorty.data.source.local.LocalDataSource
import com.metis.rickmorty.data.source.remote.RemoteDataSource
import com.metis.rickmorty.data.source.repository.Repository
import com.metis.rickmorty.data.source.repository.RepositoryImpl
import com.metis.rickmorty.domain.model.ModelCharacter
import com.metis.rickmorty.domain.model.ModelEpisode
import com.metis.rickmorty.domain.model.PageQueryResult
import com.metis.rickmorty.domain.model.QueryResult
import com.metis.rickmorty.factory.CharacterDataFactory
import com.metis.rickmorty.factory.EpisodeDataFactory
import com.metis.rickmorty.getOrAwaitValue
import com.metis.rickmorty.mapper.toModelCharacter
import com.metis.rickmorty.utils.StatusProvider
import io.mockk.MockKAnnotations
import io.mockk.every
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
    // Executes tasks in the Architecture Components in the same thread
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    // Overrides Dispatchers.Main used in Coroutines
    @get:Rule
    var coroutineRule = MainCoroutineRule()

    @MockK
    lateinit var database: LocalDataSource

    @MockK
    lateinit var api: RemoteDataSource

    @MockK
    lateinit var internetStatus: StatusProvider

    lateinit var repository: Repository

    lateinit var viewModel: EpisodeListViewModel

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
    fun `when loadEpisodes called, livedata value is set`() =
        coroutineRule.testDispatcher.runBlockingTest {
            // GIVEN
            stubStatusProviderIsOnline(isOnline = false)
            viewModel = EpisodeListViewModel(
                repository = FakeRepository(),
                statusProvider = internetStatus
            )

            // WHEN
            viewModel.loadEpisodes()

            // THEN
            assertThat(viewModel.episodes.getOrAwaitValue(), equalTo(viewModel.episodes.value))
        }

    @Test
    fun `Check if loading more activate the progress`() =
        coroutineRule.testDispatcher.runBlockingTest {
            // GIVEN
            stubStatusProviderIsOnline(isOnline = false)
            viewModel = EpisodeListViewModel(
                repository = FakeRepository(),
                statusProvider = internetStatus
            )
            // WHEN
            viewModel.loadMoreEpisodes(page = 1)

            // THEN
            assertThat(viewModel.episodes.getOrAwaitValue(), equalTo(viewModel.episodes.value))
        }

    private fun stubStatusProviderIsOnline(isOnline: Boolean) {
        every { internetStatus.isOnline() } returns isOnline
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
