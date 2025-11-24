package com.example.uma.data.repository

import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun getAllCharacters(): Flow<List<UmaCharacter>>
    fun getCharacterById(id: Int) : Flow<UmaCharacter?>
}