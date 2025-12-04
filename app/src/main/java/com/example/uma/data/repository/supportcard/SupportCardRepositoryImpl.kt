package com.example.uma.data.repository.supportcard

import android.util.Log
import com.example.uma.data.database.supportcard.SupportCardDao
import com.example.uma.data.database.supportcard.SupportCardEntity
import com.example.uma.data.network.UmaApiService
import com.example.uma.data.network.supportcards.NetworkSupportCardBasic
import com.example.uma.ui.screens.supportcard.SupportCard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import okio.IOException
import javax.inject.Inject

class SupportCardRepositoryImpl @Inject constructor(
    private val umaApiService: UmaApiService,
    private val supportCardDao: SupportCardDao,
) : SupportCardRepository {
    private fun NetworkSupportCardBasic.toSupportCardEntity(): SupportCardEntity {
        return SupportCardEntity(
            id = id,
            characterId = characterId,
            titleEn = titleEn,
            gametoraPath = gametoraPath,
            rarity = null,
            dateAdded = null,
            titleJp = null,
            cardType = null,
            typeIconUrl = null
        )
    }

    override fun getAllSupportCards(): Flow<List<SupportCardBasic>> =
        supportCardDao.getAllSupportCards()
            .onStart {
                // This block is executed when a collector starts listening.
                // It's a great place to trigger a one-time refresh.
                try {
                    val apiCards = umaApiService.getAllSupportCards()
                    supportCardDao.insertAllIgnoreExisting(apiCards.map { it.toSupportCardEntity() })
                } catch (e: IOException) {
                    // Handle potential network or API errors, e.g., log them.
                    // The Flow from the DAO will continue to emit the existing cached data.
                    Log.e("SupportCardRepo", "Error connecting to API", e)
                }
            }.map { entityList ->
                entityList.map { it.toSupportCardBasic() }
            }
            .flowOn(Dispatchers.IO)

    override suspend fun getSupportCardById(id: Int): SupportCard =
        umaApiService.getSupportCardById(id).toSupportCard()


    override suspend fun getSupportCardsByCharacterId(characterId: Int): SupportCard {
        TODO("Not yet implemented")
//        return SupportCard(
//            1, 1,
//            "", CardRarity.R,
//            0, "",
//            "", CardType.WIT, ""
//        )

    }
}