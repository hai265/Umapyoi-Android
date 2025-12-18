package com.example.uma.data.repository.character

import android.util.Log
import coil3.network.HttpException
import com.example.uma.data.database.character.CharacterDao
import com.example.uma.data.models.CharacterBasic
import com.example.uma.data.models.CharacterDetailed
import com.example.uma.data.network.UmaApiService
import com.example.uma.data.network.character.toCharacterEntity
import com.example.uma.data.network.character.toDetailedCharacterEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import okio.IOException
import javax.inject.Inject

private const val TAG = "CharacterRepositoryImpl"

class CharacterRepositoryImpl @Inject constructor(
    private val umaApiService: UmaApiService,
    private val characterDao: CharacterDao,
) : CharacterRepository {

    //TODO: Also do same thing like getCharacterDetailsById here
    override fun getAllCharacters(): Flow<List<CharacterBasic>> =
        characterDao.getAllCharacters().map { characters ->
            characters.map { it.toCharacterBasic() }
        }

    override fun getCharacterDetailsById(id: Int): Flow<CharacterDetailed> =
        characterDao.getCharacterById(id)
            .map { it.toCharacterDetailed() }
            .distinctUntilChanged() // Only emit if the data has actually changed.

    override suspend fun syncCharacterDetails(id: Int): Boolean {
        try {
            Log.i(TAG, "Fetching network character with id: $id")
            val networkResult = umaApiService.getCharacterById(id)
            Log.i(TAG, "Retrieved network character with name: ${networkResult.nameEn}")
            characterDao.syncCharacters(listOf(networkResult.toDetailedCharacterEntity()))
        } catch (e: IOException) {
            Log.e(TAG, "Network error fetching details. $e")
            return false
        } catch (e: HttpException) {
            Log.e(TAG, "HTTP error fetching details: ${e.cause}")
            return false
        }
        return true
    }

    override suspend fun sync(): Boolean {
        try {
            Log.d(TAG, "Syncing all characters")
            val characters = umaApiService.getAllCharacters().map { it.toCharacterEntity() }
            Log.d(TAG, "Fetched ${characters.size} characters")
            characterDao.syncCharacters(characters)
        } catch (e: IOException) {
            Log.e(TAG, "Error connecting $e")
            return false
        }
        return true
    }

//TODO: Add a syncCharacterDetails to pull character details
}