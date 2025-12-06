package com.example.uma

import com.example.uma.data.database.character.CharacterDao
import com.example.uma.data.repository.character.CharacterRepositoryImpl
import com.example.uma.fakes.umaList
import com.example.uma.data.network.UmaApiService
import com.example.uma.data.models.Character
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class NetworkUmaRepositoryTest {
    private val umaApiService: UmaApiService = mockk()
    private val characterDao: CharacterDao = mockk()
    private lateinit var subject: CharacterRepositoryImpl

    @Before
    fun setup() {
        coEvery { umaApiService.getAllCharacters()} returns umaList
        subject = CharacterRepositoryImpl(umaApiService, characterDao)
    }

    @Test
    fun callgetAllCharacters_getCharacters() = runTest {
        val umaList = subject.getAllCharacters()

        coVerify(exactly = 1) { umaApiService.getAllCharacters()}
        assertEquals(
            umaList, listOf<Character>(
                Character.createWithIdNameImageOnly (1, "Special Week", ""),
                Character.createWithIdNameImageOnly(2, "Tokai Teio", "")
            )
        )
    }
}