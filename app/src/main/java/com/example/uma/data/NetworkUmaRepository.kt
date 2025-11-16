package com.example.uma.data

import com.example.uma.network.UmaApiService
import com.example.uma.ui.models.UmaCharacter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

class NetworkUmaRepository @Inject constructor(
    private val umaApiService: UmaApiService
): UmaRepository {
    override suspend fun getAllCharacters(): List<UmaCharacter> {
        return umaApiService.getAllCharacters()
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkUmaRepositoryModule {
    @Binds
    abstract fun bindsUmaRepository(repository: NetworkUmaRepository): UmaRepository
}