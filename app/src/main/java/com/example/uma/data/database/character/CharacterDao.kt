package com.example.uma.data.database.character

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {
    @Query("SELECT * from characters ORDER BY nameEn ASC")
    fun getAllCharacters(): Flow<List<CharacterEntity>>

    //TODO: Make flow
    @Query("SELECT * from characters WHERE id = :id")
    fun getCharacterById(id: Int): Flow<CharacterEntity>

    @Upsert
    suspend fun upsertAll(characters: List<CharacterEntity>)

    @Query("SELECT isFavorite FROM characters WHERE id = :id")
    suspend fun isFavorite(id: Int): Boolean? // Helper to get just the favorite status

    /**
     * A "smart sync" transaction that updates character data
     * while preserving the existing `isFavorite` status.
     */
    @Transaction
    suspend fun syncCharacters(networkCharacters: List<CharacterEntity>) {
        networkCharacters.forEach { networkCharacter ->
            val currentFavoriteStatus = isFavorite(networkCharacter.id) ?: false
            val entityToSave = networkCharacter.copy(isFavorite = currentFavoriteStatus)
            upsertAll(listOf(entityToSave))
        }
    }

    @Query("UPDATE characters SET isFavorite =:isFavorite WHERE id = :id")
    suspend fun setFavorite(id: Int, isFavorite: Boolean)
}