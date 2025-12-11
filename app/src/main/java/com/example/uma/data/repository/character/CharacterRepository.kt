package com.example.uma.data.repository.character

import com.example.uma.data.models.CharacterBasic
import com.example.uma.data.models.CharacterDetailed
import com.example.uma.data.sync.Syncable
import kotlinx.coroutines.flow.Flow

interface CharacterRepository: Syncable {
    fun getAllCharacters(): Flow<List<CharacterBasic>>
    fun getCharacterDetailsById(id: Int) : Flow<CharacterDetailed>
}