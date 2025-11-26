package com.example.uma.data.repository.supportcard

import com.example.uma.ui.screens.supportcard.SupportCard
import com.example.uma.ui.screens.supportcard.SupportCardListItem

interface SupportCardRepository {
    suspend fun getAllSupportCards(): List<SupportCardListItem>
    suspend fun getSupportCardById(id: Int): SupportCard
    suspend fun getSupportCardsByCharacterId(characterId: Int): SupportCard
}