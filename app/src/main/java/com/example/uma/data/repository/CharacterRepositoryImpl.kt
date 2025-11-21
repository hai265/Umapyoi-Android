package com.example.uma.data.repository

import android.util.Log
import com.example.uma.data.database.CharacterDao
import com.example.uma.data.database.CharacterEntity
import com.example.uma.data.network.UmaApiService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import javax.inject.Inject

private const val TAG = "CharacterRepositoryImpl"
class CharacterRepositoryImpl @Inject constructor(
    private val umaApiService: UmaApiService,
    private val characterDao: CharacterDao,
): CharacterRepository {

    override fun getAllCharacters(): Flow<List<UmaCharacter>> = flow {
//        val chars = characterDao.getAllCharacters()
//        emit(chars.map { it.toUmaCharacter() })

        try {
            val result = umaApiService.getAllCharacters()
//        characterDao.insertAll(result.map { it.toCharacterEntity() })
            emit(result.map { it.toUmaCharacter() })
        } catch (e: IOException) {
            Log.e(TAG, "Error connecting $e")
        }
    }

    override fun getCharacterById(id: Int): Flow<UmaCharacter> = flow {
        emit(umaApiService.getCharacterById(id).toUmaCharacter())
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkUmaRepositoryModule {
    @Binds
    abstract fun bindsUmaRepository(repository: CharacterRepositoryImpl): CharacterRepository
}