package com.example.uma.data.repository.character

import android.util.Log
import coil3.network.HttpException
import com.example.uma.data.database.character.CharacterDao
import com.example.uma.data.models.CharacterBasic
import com.example.uma.data.models.CharacterDetailed
import com.example.uma.data.network.UmaApiService
import com.example.uma.data.network.character.toCharacterEntity
import com.example.uma.data.network.character.toDetailedCharacterEntity
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import okio.IOException
import javax.inject.Inject

private const val TAG = "CharacterRepositoryImpl"

class CharacterRepositoryImpl @Inject constructor(
    private val umaApiService: UmaApiService,
    private val characterDao: CharacterDao,
) : CharacterRepository {

    override fun getAllCharacters(): Flow<List<CharacterBasic>> =
        characterDao.getAllCharacters().map { characters ->
            characters.map { it.toCharacterBasic() }
        }

    override fun getCharacterDetailsById(id: Int): Flow<CharacterDetailed> = flow {
        val starter = characterDao.getCharacterById(id)
        emit(starter.toCharacterDetailed())

        coroutineScope {
            characterDao.getCharacterDetailsById(id)?.let { emit(it.toCharacterDetailed()) }
            try {
                val result = umaApiService.getCharacterById(id)
                characterDao.updateOrInsertCharacterDetail(result.toDetailedCharacterEntity())
                emit(result.toCharacterDetailed())
            } catch (e: IOException) {
                Log.e(TAG, "Error connecting $e")
            } catch (e: HttpException) {
                Log.e(TAG, "HTTP error fetcing details: ${e.cause}")
            }
        }

    }

    override suspend fun sync(): Boolean {
        try {
            val characters = umaApiService.getAllCharacters().map { it.toCharacterEntity() }
            characterDao.insertAllIgnoreExisting(characters)
        } catch (e: IOException) {
            Log.e(TAG, "Error connecting $e")
            return false
        }
        return true
    }
}