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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
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
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        characterDao = db.characterDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Before
    fun setup() {
        subject = CharacterRepositoryImpl(umaApiService, characterDao)
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
    //TODO: Add test directly adding to dao

}