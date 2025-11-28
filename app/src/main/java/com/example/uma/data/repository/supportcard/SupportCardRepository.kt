package com.example.uma.data.repository.supportcard

import com.example.uma.ui.screens.supportcard.SupportCard
import com.example.uma.ui.screens.supportcard.SupportCardListItem
import kotlinx.coroutines.flow.Flow

interface SupportCardRepository {
    fun getAllSupportCards(): Flow<List<SupportCardListItem>>
    suspend fun getSupportCardById(id: Int): SupportCard
    suspend fun getSupportCardsByCharacterId(characterId: Int): SupportCard
}