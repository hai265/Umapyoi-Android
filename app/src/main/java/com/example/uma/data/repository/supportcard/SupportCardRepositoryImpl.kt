package com.example.uma.data.repository.supportcard

import android.util.Log
import com.example.uma.data.database.supportcard.SupportCardDao
import com.example.uma.data.database.supportcard.SupportCardEntity
import com.example.uma.data.network.UmaApiService
import com.example.uma.data.network.supportcards.NetworkSupportCardBasic
import com.example.uma.data.network.supportcards.NetworkSupportCardDetailed
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filterNotNull
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

    override suspend fun getSupportCardsByCharacterId(characterId: Int): SupportCardDetailed {
        TODO("Not yet implemented")
//        return SupportCard(
//            1, 1,
//            "", CardRarity.R,
//            0, "",
//            "", CardType.WIT, ""
//        )

    }
}