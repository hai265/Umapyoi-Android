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
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import okio.IOException
import javax.inject.Inject

private const val TAG = "CharacterRepositoryImpl"

//TODO: This only calls network and writes to db when we run getAllCharacters
class CharacterRepositoryImpl @Inject constructor(
    private val umaApiService: UmaApiService,
    private val characterDao: CharacterDao,
) : CharacterRepository {

    override fun getAllCharacters(): Flow<List<UmaCharacter>> {
        val dbFlow = characterDao.getAllCharacters().map { dbCharacters ->
            dbCharacters.map { it.toUmaCharacter() }
        }

        return dbFlow.onStart {
            try {
                val result = umaApiService.getAllCharacters()
                characterDao.insertAll(result.map { it.toCharacterEntity() })
            } catch (e: IOException) {
                Log.e(TAG, "Error connecting $e")
            }
        }
    }


    // Also get this from the db
    override fun getCharacterById(id: Int): Flow<UmaCharacter?> = characterDao.getAllCharacters().map {
        it.firstOrNull() { it.id == id }?.toUmaCharacter()
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkUmaRepositoryModule {
    @Binds
    abstract fun bindsUmaRepository(repository: CharacterRepositoryImpl): CharacterRepository
}