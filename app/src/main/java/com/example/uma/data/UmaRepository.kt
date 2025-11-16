package com.example.uma.data

import com.example.uma.ui.models.UmaCharacter

interface UmaRepository {
    suspend fun getAllCharacters() : List<UmaCharacter>
}