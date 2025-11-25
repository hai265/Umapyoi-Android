package com.example.uma

import com.example.uma.data.repository.CharacterRepositoryImpl
import com.example.uma.fakes.umaList
import com.example.uma.data.network.UmaApiService
import com.example.uma.ui.screens.models.BasicCharacterInfo
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class NetworkUmaRepositoryTest {
    private val umaApiService: UmaApiService = mockk()
    private lateinit var subject: CharacterRepositoryImpl

    @Before
    fun setup() {
        coEvery { umaApiService.getAllCharacters()} returns umaList
        subject = CharacterRepositoryImpl(umaApiService)
    }

    @Test
    fun callgetAllCharacters_getCharacters() = runTest {
        val umaList = subject.getAllCharacters()

        coVerify(exactly = 1) { umaApiService.getAllCharacters()}
        assertEquals(
            umaList, listOf<BasicCharacterInfo>(
                BasicCharacterInfo(1, "Special Week", ""),
                BasicCharacterInfo(2, "Tokai Teio", "")
            )
        )
    }
}