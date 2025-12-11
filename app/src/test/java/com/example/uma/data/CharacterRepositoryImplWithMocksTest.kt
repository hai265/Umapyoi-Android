package com.example.uma.data

import android.util.Log
import coil3.network.HttpException
import coil3.network.NetworkResponse
import com.example.uma.data.database.character.CharacterDao
import com.example.uma.data.fakes.network.fakeNetworkCharacterDetails
import com.example.uma.data.fakes.network.fakeNetworkCharacterList
import com.example.uma.data.fakes.repository.fakeCharacterEntity1
import com.example.uma.data.fakes.repository.fakeCharacterEntity2
import com.example.uma.data.models.CharacterBasic
import com.example.uma.data.network.UmaApiService
import com.example.uma.data.network.character.toCharacterEntity
import com.example.uma.data.repository.character.CharacterRepositoryImpl
import com.example.uma.data.repository.character.toCharacterBasic
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class CharacterRepositoryImplWithMocksTest {
    private val umaApiService: UmaApiService = mockk()
    private val characterDao: CharacterDao = mockk()
    private lateinit var subject: CharacterRepositoryImpl

    @Before
    fun setup() {
        coJustRun { characterDao.updateOrInsertCharacterDetail(any()) }
        coJustRun { characterDao.insertAllIgnoreExisting(any()) }

        mockkStatic(Log::class)
        every { Log.e(any(), any<String>()) } returns 0
        every { Log.e(any(), any<String>(), any()) } returns 0 // For logs with exceptions

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

        subject.sync()

        coVerify(exactly = 1) { umaApiService.getAllCharacters() }

        val expectedEntitiesToSave = fakeNetworkCharacterList.map { it.toCharacterEntity() }
        coVerify(exactly = 1) { characterDao.insertAllIgnoreExisting(expectedEntitiesToSave) }
    }

    @Test
    fun `sync then getAllCharacters, returns newly synced data`() = runTest {
        coEvery { umaApiService.getAllCharacters() } returns fakeNetworkCharacterList

        val expectedEntitiesAfterSync = fakeNetworkCharacterList.map { it.toCharacterEntity() }
        //I don't like this mock because we don't actually test if the impl inserts the data from network
        //into dao, we just mock it and assume it happens. With a fake, it's more concrete
        coJustRun { characterDao.insertAllIgnoreExisting(any()) }
        coEvery { characterDao.getAllCharacters() } returns flowOf(expectedEntitiesAfterSync)

        subject.sync()

        val result = subject.getAllCharacters().first()

        val expected = expectedEntitiesAfterSync.map { it.toCharacterBasic() }
        assertEquals(expected, result)

        coVerify(exactly = 1) { umaApiService.getAllCharacters() }
        coVerify(exactly = 1) { characterDao.insertAllIgnoreExisting(expectedEntitiesAfterSync) }
    }

    @Test
    fun getCharacterDetailsById_fetchFromNetworkOnly() = runTest {
        coEvery { umaApiService.getCharacterById(any()) } returns fakeNetworkCharacterDetails
        coEvery { characterDao.getCharacterById(any()) } returns null
        coEvery { characterDao.getCharacterDetailsById(any()) } returns null

        val characters = subject.getCharacterDetailsById(1).toList()

        assertEquals(1, characters.size)
        assertEquals(characters.first().characterBasic.id, fakeNetworkCharacterDetails.id)
    }

    @Test
    fun getCharacterDetailsById_fetchStarterFromDaoOnly() = runTest {
        coEvery { characterDao.getCharacterById(any()) } returns fakeCharacterEntity1
        coEvery { umaApiService.getCharacterById(any()) } throws HttpException(
            response = NetworkResponse(
                code = 404
            )
        )
        coEvery { characterDao.getCharacterDetailsById(any()) } returns null

        val characters = subject.getCharacterDetailsById(1).toList()

        assertEquals(1, characters.size)
        assertEquals(characters.first().characterBasic.id, fakeNetworkCharacterDetails.id)
    }

    //TODO: Test getCharacterDetailsById already exists
    @Test
    fun getCharacterDetailsById_fetchfromBothDaoandNetwork() = runTest {
        coEvery { characterDao.getCharacterById(any()) } returns fakeCharacterEntity1
        coEvery { umaApiService.getCharacterById(any()) } throws HttpException(
            response = NetworkResponse(
                code = 404
            )
        )
        coEvery { characterDao.getCharacterDetailsById(any()) } returns fakeCharacterEntity1

        val characters = subject.getCharacterDetailsById(1).toList()

        assertEquals(3, characters.size)
        assert(characters.all { it.characterBasic.id == fakeNetworkCharacterDetails.id })
    }
}

