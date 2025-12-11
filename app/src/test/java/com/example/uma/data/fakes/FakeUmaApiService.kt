package com.example.uma.data.fakes

import com.example.uma.data.fakes.network.fakeNetworkCharacterDetails
import com.example.uma.data.fakes.network.fakeNetworkCharacterList
import com.example.uma.data.network.UmaApiService
import com.example.uma.data.network.character.NetworkCharacterDetails
import com.example.uma.data.network.character.NetworkListCharacter
import com.example.uma.data.network.supportcards.NetworkSupportCardBasic
import com.example.uma.data.network.supportcards.NetworkSupportCardDetailed

class FakeUmaApiService: UmaApiService {
    override suspend fun getAllCharacters(): List<NetworkListCharacter> {
        return fakeNetworkCharacterList
    }

    override suspend fun getCharacterById(id: Int): NetworkCharacterDetails {
        return fakeNetworkCharacterDetails
    }

    override suspend fun getAllSupportCards(): List<NetworkSupportCardBasic> {
        TODO("Not yet implemented")
    }

    override suspend fun getSupportCardById(supportCardId: Int): NetworkSupportCardDetailed {
        TODO("Not yet implemented")
    }

    override suspend fun getSupportCardByCharacterId(characterId: Int): NetworkSupportCardDetailed {
        TODO("Not yet implemented")
    }

}
