package com.example.uma.data.repository.character

import com.example.uma.ui.screens.models.Character
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun getAllCharacters(): Flow<List<Character>>
    fun getCharacterDetailsById(id: Int) : Flow<Character>
}