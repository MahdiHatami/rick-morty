package com.metis.rickmorty.data.source.repository

import com.metis.rickmorty.data.source.local.LocalDataSource
import com.metis.rickmorty.data.source.local.RMDatabase
import com.metis.rickmorty.data.source.remote.RemoteDataSource
import com.metis.rickmorty.utils.StatusProvider
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Before

open class RepositoryTest {

  @MockK
  lateinit var database: LocalDataSource

  @MockK
  lateinit var api: RemoteDataSource

  @MockK
  lateinit var internetStatus: StatusProvider

  lateinit var repository: Repository

  @Before
  fun setUp() {
    MockKAnnotations.init(this)

    repository = RepositoryImpl(
      api = api,
      db = database,
      statusProvider = internetStatus
    )
  }

  fun stubStatusProviderIsOnline(isOnline: Boolean) {
    every { internetStatus.isOnline() } returns isOnline
  }
}