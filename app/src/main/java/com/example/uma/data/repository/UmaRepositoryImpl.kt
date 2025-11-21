package com.example.uma.data.repository

import com.example.uma.data.network.NetworkUmaCharacter
import com.example.uma.data.network.UmaApiService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NetworkUmaRepository @Inject constructor(
    private val umaApiService: UmaApiService
): UmaRepository {

    override suspend fun getAllCharacters(): List<UmaCharacter> {

        val result = umaApiService.getAllCharacters()
        return result.map { it.toUmaCharacter() }
    }

    override fun getCharacterById(id: Int): Flow<UmaCharacter> = flow {
        emit(umaApiService.getCharacterById(id).toUmaCharacter())
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkUmaRepositoryModule {
    @Binds
    abstract fun bindsUmaRepository(repository: NetworkUmaRepository): UmaRepository
}