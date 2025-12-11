package com.example.uma.data

import com.example.uma.data.database.character.CharacterDao
import com.example.uma.data.fakes.fakeCharacterEntity1
import com.example.uma.data.fakes.fakeCharacterEntity2
import com.example.uma.data.fakes.fakeNetworkCharacterList
import com.example.uma.data.models.CharacterBasic
import com.example.uma.data.network.UmaApiService
import com.example.uma.data.network.character.toCharacterEntity
import com.example.uma.data.repository.character.CharacterRepositoryImpl
import com.example.uma.data.repository.character.toCharacterBasic
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

//TODO: Replace mocks with fakes? Have to test emit from dao and network and how it works on fakes vs mocks
class CharacterRepositoryImplTest {
    private val umaApiService: UmaApiService = mockk()
    private val characterDao: CharacterDao = mockk()
    private lateinit var subject: CharacterRepositoryImpl

    @Before
    fun setup() {
        subject = CharacterRepositoryImpl(umaApiService, characterDao)
    }

    @Test
    fun getAllCharacters_whenDaoHasData_returnsDataFromDao() = runTest {
        val fakeDaoData = listOf(fakeCharacterEntity1, fakeCharacterEntity2)
        coEvery { characterDao.getAllCharacters() } returns flowOf(fakeDaoData)

        val result = subject.getAllCharacters().first()

        val expected = listOf(
            CharacterBasic(1, 1, "Special Week", "image", "colorMain", "colorSub"),
            CharacterBasic(2, 2, "Tokai Teio", "image", "colorMain", "colorSub")
        )
        assertEquals(expected, result)
    }

    @Test
    fun sync_fetchesFromNetworkAndSavesToDao() = runTest {
        // 1. Mock the dependencies that will be called.
        coEvery { umaApiService.getAllCharacters() } returns fakeNetworkCharacterList
        // We need to mock upsertAll because it will be called. `justRun` means "expect a call but do nothing".
        coJustRun { characterDao.insertAllIgnoreExisting(any()) }

        subject.sync()

        coVerify(exactly = 1) { umaApiService.getAllCharacters() }

        val expectedEntitiesToSave = fakeNetworkCharacterList.map { it.toCharacterEntity() }
        coVerify(exactly = 1) { characterDao.insertAllIgnoreExisting(expectedEntitiesToSave) }
    }

    @Test
    fun `sync then getAllCharacters, returns newly synced data`() = runTest {
        // ARRANGE
        // 1. Mock the API to return the fake network list when called
        coEvery { umaApiService.getAllCharacters() } returns fakeNetworkCharacterList

        val expectedEntitiesAfterSync = fakeNetworkCharacterList.map { it.toCharacterEntity() }
        //I don't like this mock because we don't actually test if the impl inserts the data from network
        //into dao, we just mock it and assume it happens. With a fake, it's more concrete
        coJustRun { characterDao.insertAllIgnoreExisting(any()) }
        coEvery { characterDao.getAllCharacters() } returns flowOf(expectedEntitiesAfterSync)

        // ACT
        // 3. First, run the sync process to fetch from network and save to DAO
        subject.sync()

        // 4. Then, get the characters, which should now read from the DAO
        val result = subject.getAllCharacters().first()

        // ASSERT
        // 5. The result from the repository should match the data that came from the network,
        // after being mapped to the UI model.
        val expected = expectedEntitiesAfterSync.map { it.toCharacterBasic() }
        assertEquals(expected, result)

        // Optional: Also verify that the sync process actually happened
        coVerify(exactly = 1) { umaApiService.getAllCharacters() }
        coVerify(exactly = 1) { characterDao.insertAllIgnoreExisting(expectedEntitiesAfterSync) }
    }
}

