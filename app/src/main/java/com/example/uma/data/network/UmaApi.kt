package com.example.uma.data.network

import com.example.uma.ui.models.UmaCharacter
import retrofit2.Retrofit
import retrofit2.http.GET
import kotlinx.serialization.json.Json
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Provides
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.MediaType.Companion.toMediaType

private const val BASE_URL = "https://umapyoi.net/api/v1/"

interface UmaApiService {
    @GET("character/info")
    suspend fun getAllCharacters(): List<UmaCharacter>
}

@Module
@InstallIn(SingletonComponent::class)
abstract class UmaNetworkModule {
    companion object {
        @Provides
        fun providesUmaApiService(): UmaApiService {
            val json = Json {
                ignoreUnknownKeys = true // required: API returns huge objects with extra fields
            }
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
                .build()
                .create(UmaApiService::class.java)
        }
    }
}
