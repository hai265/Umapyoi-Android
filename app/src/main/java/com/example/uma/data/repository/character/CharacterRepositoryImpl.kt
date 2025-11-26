package com.example.uma.data.repository.character

import android.util.Log
import com.example.uma.data.database.character.CharacterDao
import com.example.uma.data.database.character.toCharacter
import com.example.uma.data.database.character.toUmaCharacter
import com.example.uma.data.network.UmaApiService
import com.example.uma.data.network.character.toCharacter
import com.example.uma.data.network.character.toCharacterEntity
import com.example.uma.data.network.character.toDetailedCharacterEntity
import com.example.uma.ui.screens.models.Character
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
        emit(characterDao.getAllCharacters().map { it.toUmaCharacter() })
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
        emit(
            Character.createWithIdNameImageOnly(
                id = starter.id,
                name = starter.name,
                image = starter.image,
            )
        )
        characterDao.getCharacterDetailsById(id)?.let { emit(it.toCharacter()) }
        try {
            val result = umaApiService.getCharacterById(id)
            characterDao.updateOrInsertCharacterDetail(result.toDetailedCharacterEntity())
            emit(result.toCharacter())
        } catch (e: IOException) {
            Log.e(TAG, "Error connecting $e")
        }
    }
}
