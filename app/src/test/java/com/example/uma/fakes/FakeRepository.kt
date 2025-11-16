package com.example.uma.fakes

import com.example.uma.data.UmaRepository
import com.example.uma.ui.models.UmaCharacter



class FakeRepository : UmaRepository {
    override suspend fun getAllCharacters(): List<UmaCharacter> {
        return umaList
    }
}