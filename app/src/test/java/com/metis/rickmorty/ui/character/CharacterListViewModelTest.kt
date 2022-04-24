package com.metis.rickmorty.ui.character

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.metis.rickmorty.MainCoroutineRule
import com.metis.rickmorty.factory.DataFactory
import com.metis.rickmorty.factory.FakeRepository
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
class CharacterListViewModelTest {
    // Executes tasks in the Architecture Components in the same thread
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    // Overrides Dispatchers.Main used in Coroutines
    @get:Rule
    var coroutineRule = MainCoroutineRule()

    @MockK
    lateinit var internetStatus: StatusProvider

    private lateinit var viewModel: CharacterListViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        every { internetStatus.isOnline() } returns true

        viewModel = CharacterListViewModel(
            repository = FakeRepository(),
            statusProvider = internetStatus
        )
    }

    @Test
    fun `when loadCharacters call, liveData value is set`() =
        coroutineRule.testDispatcher.runBlockingTest {
            // GIVEN
            val ids = DataFactory.randomIntList(6)
            val result = FakeRepository().getViewCharacters()

            // WHEN
            viewModel.loadCharacters(ids)

            // THEN

            assertThat(viewModel.characters.value.size, `is`(result.size))
        }

    @Test
    fun `when loadCharacters call, data must be match`() =
        coroutineRule.testDispatcher.runBlockingTest {
            // GIVEN
            val ids = DataFactory.randomIntList(6)
            val result = FakeRepository().getViewCharacters()

            // WHEN
            viewModel.loadCharacters(ids)

            // THEN

            assertThat(
                viewModel.characters.value.first().name, `is`(result.first().name)
            )
        }
}
