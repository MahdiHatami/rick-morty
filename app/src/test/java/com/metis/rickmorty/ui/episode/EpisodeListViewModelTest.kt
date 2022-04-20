package com.metis.rickmorty.ui.episode

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.metis.rickmorty.MainCoroutineRule
import com.metis.rickmorty.factory.FakeRepository
import com.metis.rickmorty.getOrAwaitValue
import com.metis.rickmorty.utils.StatusProvider
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
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
    lateinit var internetStatus: StatusProvider

    private lateinit var viewModel: EpisodeListViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        every { internetStatus.isOnline() } returns true

        viewModel = EpisodeListViewModel(
            repository = FakeRepository(),
            statusProvider = internetStatus
        )
    }

    @Test
    fun `when loadEpisodes called, livedata value is set`() =
        coroutineRule.testDispatcher.runBlockingTest {
            // GIVEN
            val result = FakeRepository().getViewEpisodes()

            // WHEN
            viewModel.loadEpisodes()

            // THEN
            assertThat(viewModel.episodes.getOrAwaitValue().size, `is`(result.size))
        }

    @Test
    fun `when loadEpisodes called, data must match`() =
        coroutineRule.testDispatcher.runBlockingTest {
            // GIVEN
            val result = FakeRepository().getViewEpisodes()

            // WHEN
            viewModel.loadEpisodes()

            // THEN
            assertThat(viewModel.episodes.getOrAwaitValue().first().name, `is`(result.first().name))
        }
}
