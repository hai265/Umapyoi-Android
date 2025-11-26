package com.example.uma.data.repository

import android.util.Log
import com.example.uma.data.database.character.CharacterDao
import com.example.uma.data.database.character.CharacterDetailEntity
import com.example.uma.data.database.character.CharacterEntity
import com.example.uma.data.database.character.toCharacter
import com.example.uma.data.database.character.toUmaCharacter
import com.example.uma.data.network.NetworkCharacterDetails
import com.example.uma.data.network.NetworkListCharacter
import com.example.uma.data.network.UmaApiService
import com.example.uma.data.network.toCharacter
import com.example.uma.data.network.toCharacterEntity
import com.example.uma.data.network.toDetailedCharacterEntity
import com.example.uma.ui.screens.models.BirthDate
import com.example.uma.ui.screens.models.Character
import com.example.uma.ui.screens.models.CharacterProfile
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import javax.inject.Inject
import kotlin.collections.map

private const val TAG = "CharacterRepositoryImpl"

class CharacterRepositoryImpl @Inject constructor(
    private val umaApiService: UmaApiService,
    private val characterDao: CharacterDao,
) : CharacterRepository {

    override fun getAllCharacters(): Flow<List<Character>> = flow {
        emit(characterDao.getAllCharacters().map{it.toUmaCharacter()})
        try {
            val characters = umaApiService.getAllCharacters().map { it.toCharacterEntity() }
            characterDao.insertAllIgnoreExisting(characters)
            emit(characters.map { it.toUmaCharacter() })
        } catch (e: IOException) {
            Log.e(TAG, "Error connecting $e")
        }
    }

    override fun getCharacterDetailsById(id: Int): Flow<Character> = flow {
        val starter = characterDao.getAllCharacters().first { it.id == id }
        emit (Character.createWithIdNameImageOnly(
            id = starter.id,
            name = starter.name,
            image = starter.image,
        ))
        characterDao.getCharacterDetailsById(id)?.let {emit (it.toCharacter())}
        try {
            val result = umaApiService.getCharacterById(id)
            characterDao.updateOrInsertCharacterDetail(result.toDetailedCharacterEntity())
            emit(result.toCharacter())
        } catch (e: IOException) {
            Log.e(TAG, "Error connecting $e")
        }
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkUmaRepositoryModule {
    @Binds
    abstract fun bindsUmaRepository(repository: CharacterRepositoryImpl): CharacterRepository
}