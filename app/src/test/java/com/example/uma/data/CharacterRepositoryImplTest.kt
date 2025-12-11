package com.example.uma.data

import com.example.uma.data.database.character.CharacterDao
import com.example.uma.data.fakes.fakeCharacterEntity1
import com.example.uma.data.fakes.fakeCharacterEntity2
import com.example.uma.data.fakes.umaList
import com.example.uma.data.models.CharacterBasic
import com.example.uma.data.network.UmaApiService
import com.example.uma.data.repository.character.CharacterRepositoryImpl
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
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
        coEvery { umaApiService.getAllCharacters() } returns umaList
        coEvery { characterDao.getAllCharacters() } returns flow {
            emit(listOf(fakeCharacterEntity1, fakeCharacterEntity2))
        }
        subject = CharacterRepositoryImpl(umaApiService, characterDao)
    }

    @Test
    fun callgetAllCharacters_getCharacters() = runTest {
        val umaList = subject.getAllCharacters().first()

//        coVerify(exactly = 1) { umaApiService.getAllCharacters() }

        assertEquals(
            listOf(
                CharacterBasic(1, 1, "Special Week", "image", "colorMain", "colorSub"),
                CharacterBasic(2, 2, "Tokai Teio", "image", "colorMain", "colorSub")
            ),
            umaList,
        )
    }
}

