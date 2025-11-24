package com.example.uma.data.repository

import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun getAllCharacters(): Flow<List<ListCharacter>>
    fun getCharacterById(id: Int) : Flow<ListCharacter?>
}