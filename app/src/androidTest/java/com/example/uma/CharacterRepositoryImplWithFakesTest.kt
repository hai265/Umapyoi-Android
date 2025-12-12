package com.example.uma

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.example.uma.data.database.AppDatabase
import com.example.uma.data.database.character.CharacterDao
import com.example.uma.data.models.CharacterBasic
import com.example.uma.data.network.UmaApiService
import com.example.uma.data.repository.character.CharacterRepositoryImpl
import com.example.uma.fakes.FakeUmaApiService
import com.example.uma.fakes.fakeCharacterBasic
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CharacterRepositoryImplWithFakesTest {
    private val umaApiService: UmaApiService = FakeUmaApiService()
    private lateinit var characterDao: CharacterDao
    private lateinit var subject: CharacterRepositoryImpl

    private lateinit var db: AppDatabase
    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        characterDao = db.characterDao()

        subject = CharacterRepositoryImpl(umaApiService, characterDao)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun sync_fetchesFromNetworkAndSavesToDao() = runTest {
        subject.sync()

        val character = subject.getAllCharacters().first()

        val expected = listOf(
            CharacterBasic(1, 1, "Special Week", "image", "colorMain", "colorSub"),
            CharacterBasic(2, 2, "Tokai Teio", "image", "colorMain", "colorSub")
        )
        assertEquals(expected, character)
    }

    //TODO: fix no emission in 3s
    @Test
    fun syncTwice_getAllCharactersEmitsOne() = runTest {
        subject.getAllCharacters().test {
            subject.sync()
            val firstEmission = awaitItem()
            assertEquals(2, firstEmission.size)

            subject.sync()
            val secondEmission = awaitItem()
            assertEquals(2, secondEmission.size)
        }
    }

    @Test
    fun getCharacterDetailsById_getStarter_thenGetNetworkDetailed() = runTest {
        subject.getCharacterDetailsById(1).test {
            subject.sync()

            val starterCharacter = awaitItem()
            assertEquals(fakeCharacterBasic, starterCharacter.characterBasic)
            //No slogan since slogan since slogan field doesn't yet
            //TODO: Move category to characterBasic
            assertNull(starterCharacter.characterProfile.slogan)

            val detailedCharacter = awaitItem()
            assertEquals("Detailed character ID should be correct", 1, detailedCharacter.characterBasic.id)
            assertEquals("Detailed character should now have a slogan", "I'll be the number one horse girl in Japan!", detailedCharacter.characterProfile.slogan)

            cancelAndIgnoreRemainingEvents()
        }
    }
    //TODO: Add test directly adding to dao

}