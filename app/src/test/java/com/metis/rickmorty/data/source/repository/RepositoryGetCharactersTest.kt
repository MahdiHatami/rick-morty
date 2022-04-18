package com.metis.rickmorty.data.source.repository

import com.metis.rickmorty.data.model.ApiCharacter
import com.metis.rickmorty.data.model.ApiResult
import com.metis.rickmorty.data.source.local.entity.DbCharacter
import com.metis.rickmorty.factory.ApiFactory
import com.metis.rickmorty.factory.CharacterDataFactory
import com.metis.rickmorty.factory.DataFactory
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.runs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RepositoryGetCharactersTest : RepositoryTest() {

    @Test
    fun `getCharacters call api and db when device isOnline`() = runBlockingTest {
        // GIVEN
        val characterIds: List<Int> = DataFactory.randomIntList(count = 5)
        stubStatusProviderIsOnline(true)
        stubApiFetchCharacters(ApiFactory.Characters.makeCharacters())
        val entityCharacters = CharacterDataFactory.makeDbCharacters(5)
        stubCharacterDaoQueryAllCharacterByIds(entityCharacters)
        stubDbInsertCharacters()

        // WHEN
        repository.getCharactersByIds(characterIds = characterIds)

        // THEN
        coVerify(exactly = 1) { api.fetchCharactersByIds(ids = any()) }
        coVerify(exactly = 1) { database.insertCharacter(entityCharacter = any()) }
        coVerify(exactly = 1) { database.queryCharacterByIds(characterIds = any()) }
    }

    @Test
    fun `getCharacters call on db when device isOffline`() = runBlockingTest {
        // GIVEN
        val characterIds: List<Int> = DataFactory.randomIntList(count = 5)
        stubStatusProviderIsOnline(false)
        stubApiFetchCharacters(ApiFactory.Characters.makeCharacters())
        val entityCharacters = CharacterDataFactory.makeDbCharacters(5)
        stubCharacterDaoQueryAllCharacterByIds(entityCharacters)
        stubDbInsertCharacters()

        // WHEN
        repository.getCharactersByIds(characterIds = characterIds)

        // THEN
        coVerify(exactly = 0) { api.fetchCharactersByIds(ids = any()) }
        coVerify(exactly = 1) { database.queryCharacterByIds(characterIds = any()) }
    }

    @Test
    fun `getCharacter details call on db`() = runBlockingTest {
        // GIVEN
        val entityCharacter = CharacterDataFactory.makeDbCharacter(1)
        stubCharacterDaoQueryCharacterDetailsById(entityCharacter)

        // WHEN
        repository.getCharacterDetails(characterId = 1)

        // THEN
        coVerify(exactly = 0) { api.fetchCharacterDetails(id = any()) }
        coVerify(exactly = 1) { database.queryCharacterById(id = any()) }
    }

    private fun stubCharacterDaoQueryAllCharacterByIds(entityCharacters: List<DbCharacter>) {
        coEvery {
            database.queryCharacterByIds(characterIds = any())
        } returns entityCharacters
    }

    private fun stubCharacterDaoQueryCharacterDetailsById(entityCharacter: DbCharacter) {
        coEvery {
            database.queryCharacterById(id = any())
        } returns entityCharacter
    }

    private fun stubApiFetchCharacters(characters: List<ApiCharacter>) {
        coEvery { api.fetchCharactersByIds(any()) } returns ApiResult.Success(characters)
    }

    private fun stubDbInsertCharacters() {
        coEvery { database.insertCharacter(any()) } just runs
    }
}
