package com.example.uma

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.uma.data.database.AppDatabase
import com.example.uma.data.database.character.CharacterDao
import com.example.uma.data.network.UmaApiService
import com.example.uma.data.repository.character.CharacterRepositoryImpl
import com.example.uma.fakes.FakeUmaApiService
import org.junit.After
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
            context, AppDatabase::class.java).build()
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
    @Throws(Exception::class)
    fun writeUserAndReadInList() {

    }

}