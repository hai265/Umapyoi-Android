package com.example.uma.data.network

import com.example.uma.ui.models.UmaCharacter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

class NetworkUmaRepository @Inject constructor(
    private val umaApiService: UmaApiService
): UmaRepository {
    private var cachedCharacters: List<UmaCharacter>? = null

    override suspend fun getAllCharacters(): List<UmaCharacter> {
        cachedCharacters?.let { return it }

        val result = umaApiService.getAllCharacters()
        cachedCharacters = result
        return result
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkUmaRepositoryModule {
    @Binds
    abstract fun bindsUmaRepository(repository: NetworkUmaRepository): UmaRepository
}