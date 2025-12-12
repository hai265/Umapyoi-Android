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
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
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

    override fun getCharacterDetailsById(id: Int): Flow<CharacterDetailed> = flow {
        try {
            emit(
                characterDao.getCharacterById(id)
                .map { it.toCharacterDetailed() }
                .first()
            )
        } catch (e: NoSuchElementException) {
            Log.i(TAG, "Character with id $id not found")
        }

        // --- Step 2: Fetch fresh data from network in the background ---
        try {
            Log.d(TAG, "Fetching fresh network data for id: $id")
            val networkResult = umaApiService.getCharacterById(id)
            // Save the fresh data. This will trigger the main flow to re-emit if it's still being collected.
            characterDao.updateOrInsertCharacterDetail(networkResult.toDetailedCharacterEntity())
        } catch (e: IOException) {
            Log.e(TAG, "Network error fetching details. $e")
        } catch (e: HttpException) {
            Log.e(TAG, "HTTP error fetching details: ${e.cause}")
        }

        emitAll(
            characterDao.getCharacterById(id)
                .map { it.toCharacterDetailed() }
                .distinctUntilChanged() // Only emit if the data has actually changed.
        )
    }

    override suspend fun sync(): Boolean {
        try {
            val characters = umaApiService.getAllCharacters().map { it.toCharacterEntity() }
            characterDao.upsertAll(characters)
        } catch (e: IOException) {
            Log.e(TAG, "Error connecting $e")
            return false
        }
        return true
    }

    //TODO: Add a syncCharacterDetails to pull character details
}