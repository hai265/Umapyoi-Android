package com.example.uma.data.repository

import com.example.uma.ui.screens.models.BasicCharacterInfo
import com.example.uma.ui.screens.models.DetailedCharacterInfo
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun getAllCharacters(): Flow<List<BasicCharacterInfo>>
    fun getCharacterDetailsById(id: Int) : Flow<DetailedCharacterInfo>
}