package com.metis.rickmorty.data.source.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.metis.rickmorty.MainCoroutineRule
import com.metis.rickmorty.data.source.local.LocalDataSource
import com.metis.rickmorty.data.source.local.RMDatabase
import com.metis.rickmorty.data.source.remote.RemoteDataSource
import com.metis.rickmorty.utils.StatusProvider
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Rule

open class RepositoryTest {

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
      dispatcher = coroutineRule.testDispatcher
    )
  }

  fun stubStatusProviderIsOnline(isOnline: Boolean) {
    every { internetStatus.isOnline() } returns isOnline
  }
}