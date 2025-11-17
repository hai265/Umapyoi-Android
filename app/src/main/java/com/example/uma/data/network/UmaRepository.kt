package com.example.uma.data.network

import com.example.uma.ui.models.UmaCharacter

interface UmaRepository {
    suspend fun getAllCharacters() : List<UmaCharacter>
}