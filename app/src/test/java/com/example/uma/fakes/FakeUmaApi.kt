package com.example.uma.fakes

import com.example.uma.network.UmaApiService
import com.example.uma.ui.models.UmaCharacter

val umaList = listOf<UmaCharacter>(
    UmaCharacter("Special Week", ""),
    UmaCharacter("Tokai Teio", "")
)

class FakeUmaApi: UmaApiService {
    override suspend fun getAllCharacters(): List<UmaCharacter> {
        return umaList
    }
}