package com.example.uma.data.repository.supportcard

import com.example.uma.data.database.supportcard.SupportCardDao
import com.example.uma.data.network.UmaApiService
import com.example.uma.data.network.supportcards.NetworkSupportCardBasic
import com.example.uma.data.network.supportcards.SupportCardDetailed
import com.example.uma.ui.screens.supportcard.CardRarity
import com.example.uma.ui.screens.supportcard.SupportCard
import com.example.uma.ui.screens.supportcard.CardType
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

private const val GAMETORA_IMAGE_URL =
    "https://gametora.com/images/umamusume/supports/tex_support_card"

class SupportCardRepositoryImpl @Inject constructor(
    private val umaApiService: UmaApiService,
    private val supportCardDao: SupportCardDao,
) : SupportCardRepository {
    override fun getAllSupportCards(): Flow<List<SupportCardBasic>> = flow {

        //TODO: Insert all cards from api here
        coroutineScope {
            val apiCards = umaApiService.getAllSupportCards()
            supportCardDao.getAllSupportCards()
        }
        emit(umaApiService.getAllSupportCards().map { it.toSupportCardBasic() })
    }

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

private fun SupportCardDetailed.toSupportCard(): SupportCard {
    return SupportCard(
        id = id,
        characterId = characterId,
        imageUrl = gameToraImageUrl(id),
        rarity = CardRarity.fromInt(rarity),
        dateAdded = startDate.toLong(),
        titleEn = titleEn,
        titleJp = titleJp,
        cardType = CardType.fromString(type),
        typeIconUrl = typeIconUrl
    )
}

private fun NetworkSupportCardBasic.toSupportCardBasic(): SupportCardBasic {
    return SupportCardBasic(
        id = id,
        characterId = characterId,
        imageUrl = gameToraImageUrl(id), //TODO: Pass  gametora imageurl here
    )
}


//TODO: Move to core package
/**
 * @param id internal id NOT game id
 * **/
private fun gameToraImageUrl(id: Int): String {
    return "${GAMETORA_IMAGE_URL}_$id.png"
}
