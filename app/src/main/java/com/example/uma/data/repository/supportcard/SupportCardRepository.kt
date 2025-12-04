package com.example.uma.data.repository.supportcard

import com.example.uma.data.sync.Syncable
import com.example.uma.ui.screens.supportcard.SupportCard
import kotlinx.coroutines.flow.Flow

interface SupportCardRepository: Syncable {
    fun getAllSupportCards(): Flow<List<SupportCardBasic>>
    suspend fun getSupportCardById(id: Int): SupportCard
    suspend fun getSupportCardsByCharacterId(characterId: Int): SupportCard
}