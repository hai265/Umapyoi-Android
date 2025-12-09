package com.example.uma.data.repository.supportcard

import android.util.Log
import com.example.uma.data.database.supportcard.SupportCardDao
import com.example.uma.data.models.SupportCardBasic
import com.example.uma.data.models.SupportCardDetailed
import com.example.uma.data.network.UmaApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import okio.IOException
import javax.inject.Inject

class SupportCardRepositoryImpl @Inject constructor(
    private val umaApiService: UmaApiService,
    private val supportCardDao: SupportCardDao,
) : SupportCardRepository {
    override fun getAllSupportCards(): Flow<List<SupportCardBasic>> =
        supportCardDao.getAllSupportCards().map { supportCardEntities ->
            supportCardEntities.map { it.toSupportCardBasic() }
        }

    //    Ideally we want to call this in some syncer class on startup
    override suspend fun sync(): Boolean {
        try {
            val apiCards = umaApiService.getAllSupportCards()
            supportCardDao.insertAllIgnoreExisting(apiCards.map { it.toSupportCardEntity() })
        } catch (e: IOException) {
            Log.e("SupportCardRepo", "Error connecting to API", e)
            return false
        }
        return true
    }

    override fun getSupportCardById(id: Int): Flow<SupportCardDetailed> = flow {
        supportCardDao.getSupportCardById(id)
            .filterNotNull()
            .take(1)
            .collect {
                emit(it.toSupportCard())
            }

        try {
            val supportCard = umaApiService.getSupportCardById(id)
            supportCardDao.insertAndReplace(supportCard.toSupportCardEntity())
        } catch (e: IOException) {
            Log.e("SupportCardRepo", "Error connecting to API", e)
        }
        emitAll(supportCardDao.getSupportCardById(id).map { it.toSupportCard() })

    }

    override suspend fun getSupportCardsByCharacterId(characterId: Int?): List<SupportCardBasic> {
        if (characterId != null) {
            return supportCardDao.getSupportCardsByCharacterId(characterId).map { supportCardList ->
                supportCardList.map { it.toSupportCardBasic() }
            }.first()
        } else {
            return listOf()
        }
    }
}