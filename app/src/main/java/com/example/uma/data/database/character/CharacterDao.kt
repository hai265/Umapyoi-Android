package com.example.uma.data.database.character

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateOrInsertCharacterDetail(character: CharacterEntity)
}