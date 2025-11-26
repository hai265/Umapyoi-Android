package com.example.uma.data.network

import com.example.uma.data.network.character.NetworkCharacterDetails
import com.example.uma.data.network.character.NetworkListCharacter
import com.example.uma.data.network.supportcards.SupportCardBasic
import com.example.uma.data.network.supportcards.SupportCardDetailed
import retrofit2.Retrofit
import retrofit2.http.GET
import kotlinx.serialization.json.Json
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Provides
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.http.Path

private const val BASE_URL = "https://umapyoi.net/api/v1/"

interface UmaApiService {
    @GET("character/info")
    suspend fun getAllCharacters(): List<NetworkListCharacter>

    @GET("character/{id}")
    suspend fun getCharacterById(
        @Path("id") id: Int
    ): NetworkCharacterDetails

    @GET("support")
    suspend fun getAllSupportCards(): List<SupportCardBasic>

    @GET("support/{id}")
    suspend fun getSupportCardById(supportCardId: Int): SupportCardDetailed

    @GET("support/character/{id}")
    suspend fun getSupportCardByCharacterId(characterId: Int): SupportCardDetailed
}

@Module
@InstallIn(SingletonComponent::class)
abstract class UmaNetworkModule {
    companion object {
        @Provides
        fun providesUmaApiService(): UmaApiService {
            val json = Json {
                ignoreUnknownKeys = true // required: API returns huge objects with extra fields
                coerceInputValues = true
            }
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
                .build()
                .create(UmaApiService::class.java)
        }
    }
}
