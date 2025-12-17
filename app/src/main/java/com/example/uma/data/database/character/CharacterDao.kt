package com.example.uma.data.database.character

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
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

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllIgnoreExisting(characters: List<CharacterEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateOrInsertCharacterDetail(character: CharacterEntity)


    @Query("SELECT isFavorite FROM characters WHERE id = :id")
    suspend fun isFavorite(id: Int): Boolean? // Helper to get just the favorite status

    @Upsert
    suspend fun upsertAll(characters: List<CharacterEntity>)

    /**
     * A "smart sync" transaction that updates character data
     * while preserving the existing `isFavorite` status.
     */
    //TODO: Replace usages of insertAllIgnoreExisting and updateOrInsertCharacterDetail with syncCharacters
    @Transaction
    suspend fun syncCharacters(networkCharacters: List<CharacterEntity>) {
        networkCharacters.forEach { networkCharacter ->
            val currentFavoriteStatus = isFavorite(networkCharacter.id) ?: false

            val entityToSave = networkCharacter.copy(isFavorite = currentFavoriteStatus)

            upsertAll(listOf(entityToSave))
        }
    }
}