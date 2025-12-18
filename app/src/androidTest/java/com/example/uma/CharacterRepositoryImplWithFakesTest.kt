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
import com.example.uma.fakes.repository.fakeCharacterEntity1
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
            CharacterBasic(1, 1, "Special Week", "image", "colorMain", "colorSub",),
            CharacterBasic(2, 2, "Tokai Teio", "image", "colorMain", "colorSub",)
        )
        assertEquals(expected, character)
    }

    //TODO: Fix to test only get one emission
    @Test
    fun syncTwice_getAllCharactersEmitsOne() = runTest {
        subject.getAllCharacters().test {
            val initialEmission = awaitItem()
            assertEquals("Flow should initially be empty", 0, initialEmission.size)
            //TODO: Replace with inserting in dao directly
            subject.sync()
            val firstEmission = awaitItem()
            assertEquals(2, firstEmission.size)

            subject.sync()
            expectNoEvents()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun getCharacterDetailsById_getStarter_thenGetNetworkDetailed() = runTest {
        //Populate with starter data first
        subject.getCharacterDetailsById(1).test {
            val starterEntity = fakeCharacterEntity1.copy(slogan = null)
            characterDao.syncCharacters(listOf(starterEntity))

            val starter = awaitItem()
            assertNull(starter.characterProfile.slogan)
            subject.syncCharacterDetails(1)
            val detailed = awaitItem()

            assertEquals("I'll be the number one horse girl in Japan!", detailed.characterProfile.slogan)
        }
    }
    //TODO: Add test syncwith doesn't override favorite
    //TODO: Add test set favorite

}