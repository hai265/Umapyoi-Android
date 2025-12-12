package com.example.uma

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.uma.data.database.AppDatabase
import com.example.uma.data.database.supportcard.SupportCardDao
import com.example.uma.data.network.UmaApiService
import com.example.uma.data.repository.supportcard.SupportCardRepositoryImpl
import com.example.uma.fakes.FakeUmaApiService
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SupportCardRepositoryImplTest {
    private val umaApiService: UmaApiService = FakeUmaApiService()
    private lateinit var supportCardDao: SupportCardDao

    private lateinit var subject: SupportCardRepositoryImpl
    private lateinit var db: AppDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        supportCardDao = db.supportCardDao()

        subject = SupportCardRepositoryImpl(umaApiService, supportCardDao)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun test() {

    }

}