package com.example.uma.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {
    @Query("SELECT * from characters ORDER BY name ASC")
    suspend fun getAllCharacters(): List<CharacterEntity>

    @Insert
    fun insertAll(characters: List<CharacterEntity>)
}