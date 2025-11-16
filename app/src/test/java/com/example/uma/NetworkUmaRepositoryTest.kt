package com.example.uma

import com.example.uma.data.NetworkUmaRepository
import com.example.uma.fakes.FakeUmaApi
import com.example.uma.ui.models.UmaCharacter
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class NetworkUmaRepositoryTest {
    private lateinit var subject: NetworkUmaRepository

//    TODO: add mocks

    @Before
    fun setup() {
        subject = NetworkUmaRepository(FakeUmaApi())
    }

    @Test
    fun callgetAllCharacters_getCharacters() = runTest {
        val umaList = subject.getAllCharacters()

        assertEquals(
            umaList, listOf<UmaCharacter>(
                UmaCharacter("Special Week", ""),
                UmaCharacter("Tokai Teio", "")
            )
        )
    }
}