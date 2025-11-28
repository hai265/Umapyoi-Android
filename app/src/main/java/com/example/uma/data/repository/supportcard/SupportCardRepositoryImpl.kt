package com.example.uma.data.repository.supportcard

import com.example.uma.data.network.UmaApiService
import com.example.uma.data.network.supportcards.NetworkSupportCardBasic
import com.example.uma.ui.screens.supportcard.CardRarity
import com.example.uma.ui.screens.supportcard.SupportCard
import com.example.uma.ui.screens.supportcard.Type
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SupportCardRepositoryImpl @Inject constructor(
    private val umaApiService: UmaApiService,
) : SupportCardRepository {
    override fun getAllSupportCards(): Flow<List<SupportCardBasic>> = flow {
        emit(umaApiService.getAllSupportCards().map { it.toSupportCardBasic() })
    }

    override suspend fun getSupportCardById(id: Int): SupportCard {
        return SupportCard(
            1, 1,
            "", CardRarity.R,
            0, "",
            "", Type.WIT, ""
        )
        TODO("Not yet implemented")
    }

    override suspend fun getSupportCardsByCharacterId(characterId: Int): SupportCard {
        return SupportCard(
            1, 1,
            "", CardRarity.R,
            0, "",
            "", Type.WIT, ""
        )
        TODO("Not yet implemented")
    }
}

private fun NetworkSupportCardBasic.toSupportCardBasic(): SupportCardBasic {
    return SupportCardBasic(
        id = id,
        characterId = characterId,
        imageUrl = gametoraPath.toGametoraImageLink(), //TODO: Pass  gametora imageurl here
    )
}

private fun String.toGametoraImageLink(): String {
    return "https://gametora.com/images/umamusume/supports/tex_support_card $this.png"
}
