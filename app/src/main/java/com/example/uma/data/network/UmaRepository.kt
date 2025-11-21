package com.example.uma.data.network

import com.example.uma.ui.models.UmaCharacter
import kotlinx.coroutines.flow.Flow

interface UmaRepository {
    suspend fun getAllCharacters() : List<UmaCharacter>
    fun getCharacterById(id: Int) : Flow<UmaCharacter>
}