package com.example.uma.data.repository.character

import com.example.uma.data.sync.Syncable
import com.example.uma.data.models.Character
import kotlinx.coroutines.flow.Flow

interface CharacterRepository: Syncable {
    fun getAllCharacters(): Flow<List<Character>>
    fun getCharacterDetailsById(id: Int) : Flow<Character>
}