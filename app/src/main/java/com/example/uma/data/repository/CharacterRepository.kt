package com.example.uma.data.repository

import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    suspend fun getAllCharacters() : List<UmaCharacter>
    fun getCharacterById(id: Int) : Flow<UmaCharacter>
}