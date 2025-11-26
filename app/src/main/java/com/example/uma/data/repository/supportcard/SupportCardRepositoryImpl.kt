package com.example.uma.data.repository.supportcard

import com.example.uma.data.network.UmaApiService
import com.example.uma.data.network.supportcards.SupportCardBasic
import com.example.uma.ui.screens.models.SupportCard
import com.example.uma.ui.screens.models.SupportCardListItem
import javax.inject.Inject

class SupportCardRepositoryImpl @Inject constructor(
    private val umaApiService: UmaApiService,
): SupportCardRepository {
    override suspend fun getAllSupportCards(): List<SupportCardListItem> {
        return umaApiService.getAllSupportCards().map { it.toSupportCardListItem() }
    }

    override suspend fun getSupportCardById(id: Int): SupportCard {
        TODO("Not yet implemented")
    }

    override suspend fun getSupportCardsByCharacterId(characterId: Int): SupportCard {
        TODO("Not yet implemented")
    }
}

private fun SupportCardBasic.toSupportCardListItem(): SupportCardListItem {
    return SupportCardListItem(
        id = id,
        characterId = characterId,
        imageUrl = gametoraPath.toGametoraImageLink(), //TODO: Pass  gametora imageurl here
        title = titleEn
    )
}

private fun String.toGametoraImageLink(): String {
    return "https://gametora.com/images/umamusume/supports/tex_support_card $this.png"
}
