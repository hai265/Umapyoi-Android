package com.example.uma

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.example.uma.data.database.AppDatabase
import com.example.uma.data.database.supportcard.SupportCardDao
import com.example.uma.data.models.CardType
import com.example.uma.data.network.UmaApiService
import com.example.uma.data.repository.supportcard.SupportCardRepositoryImpl
import com.example.uma.data.repository.supportcard.toSupportCardEntity
import com.example.uma.fakes.FakeUmaApiService
import com.example.uma.fakes.network.fakeNetworkSupportCardBasic
import junit.framework.Assert.assertNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
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
            context, AppDatabase::class.java
        )
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
    fun sync_fetchesFromNetworkAndSavesToDao() = runTest {
        subject.sync()

        val supportCards = subject.getAllSupportCards().first()

        assertEquals(1, supportCards[0].id)
        assertEquals(2, supportCards[1].id)
    }

    @Test
    fun syncTwice_getAllCharactersEmitsOne() = runTest {
        subject.getAllSupportCards().test {
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
    fun getS_getSupportCardByIdGetStarter_thenGetNetworkDetailed() = runTest {
        //Populate with starter data first
        supportCardDao.insertAllIgnoreExisting(
            listOf(
                fakeNetworkSupportCardBasic.toSupportCardEntity()
            )
        )
        subject.getSupportCardById(1).test {
            val starter = awaitItem()

            assertEquals(1, starter.id)
            assertNull(starter.cardType)

            val detailed = awaitItem()
            assertEquals(1, detailed.id)
            assertEquals(
                CardType.GUTS,
                detailed.cardType
            )
        }
    }

}