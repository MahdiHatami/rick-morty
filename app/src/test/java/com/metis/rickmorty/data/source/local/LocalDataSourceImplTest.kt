package com.metis.rickmorty.data.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.metis.rickmorty.MainCoroutineRule
import com.metis.rickmorty.factory.CharacterDataFactory
import com.metis.rickmorty.factory.EpisodeDataFactory
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.notNullValue
import org.hamcrest.core.IsEqual
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class LocalDataSourceImplTest {

  @MockK
  lateinit var localDataSource: LocalDataSource
  lateinit var database: RMDatabase

  // Executes tasks in the Architecture Components in the same thread
  @get:Rule
  var instantTaskExecutorRule = InstantTaskExecutorRule()

  // Overrides Dispatchers.Main used in Coroutines
  @get:Rule
  var coroutineRule = MainCoroutineRule()

  @Before
  fun createDb() {
    // using an in-memory database for testing, since it doesn't survive killing the process
    database = Room.inMemoryDatabaseBuilder(
      ApplicationProvider.getApplicationContext(),
      RMDatabase::class.java
    )
      .allowMainThreadQueries()
      .build()

    localDataSource = LocalDataSourceImpl(
      episodeDao = database.episodeDao,
      characterDao = database.characterDao,
      ioDispatcher = coroutineRule.testDispatcher
    )
  }

  @After
  @Throws(IOException::class)
  fun closeDb() {
    database.close()
  }

  @Test
  @Throws(Exception::class)
  fun insertEpisode() = runBlockingTest {
    val episode = EpisodeDataFactory.makeDbEpisode()

    localDataSource.insertEpisode(episode)
    val dbEpisode = localDataSource.queryAllEpisodesByPage(1, 1)

    assertThat(dbEpisode.first().name, notNullValue())
  }

  @Test
  fun queryAllEpisodesByPage() = runBlockingTest {
    // GIVEN
    val episodeList = EpisodeDataFactory.makeDbEpisodes(2)
    episodeList.forEach {
      localDataSource.insertEpisode(it)
    }

    // WHEN
    val dbEpisodeList = localDataSource.queryAllEpisodesByPage(1, 2)

    // THEN
    assertThat(dbEpisodeList.size, equalTo(episodeList.size))
  }

  @Test
  fun insertCharacter() = runBlockingTest {
    // GIVEN
    val character = CharacterDataFactory.makeDbCharacter()
    localDataSource.insertCharacter(character)

    // WHEN
    val dbcharacter = localDataSource.queryCharacterById(character.id)

    // THEN
    assertThat(dbcharacter, notNullValue())
  }

  @Test
  fun queryCharacterByIds() = runBlockingTest {
    // GIVEN
    val characters = CharacterDataFactory.makeDbCharacters(2)
    characters.forEach {
      localDataSource.insertCharacter(it)
    }

    // WHEN
    val dbCharacters = localDataSource.queryCharacterByIds(characters.map { it.id })

    // THEN
    assertThat(dbCharacters, notNullValue())
    assertThat(dbCharacters.size, `is`(characters.size))
  }

  @Test
  fun queryCharacterById() = runBlockingTest {
    // GIVEN
    val character = CharacterDataFactory.makeDbCharacter()
    localDataSource.insertCharacter(character)

    // WHEN
    val dbCharacter = localDataSource.queryCharacterById(character.id)

    // THEN
    assertThat(dbCharacter, notNullValue())
    assertThat(dbCharacter?.id, equalTo(character.id))
  }
}