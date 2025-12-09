package com.example.uma.data.repository.supportcard

import com.example.uma.data.models.SupportCardBasic
import com.example.uma.data.models.SupportCardDetailed
import com.example.uma.data.sync.Syncable
import kotlinx.coroutines.flow.Flow

interface SupportCardRepository: Syncable {
    fun getAllSupportCards(): Flow<List<SupportCardBasic>>
    fun getSupportCardById(id: Int): Flow<SupportCardDetailed>
    suspend fun getSupportCardsByCharacterId(characterId: Int?): List<SupportCardBasic>
}