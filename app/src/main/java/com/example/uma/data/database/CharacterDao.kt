package com.example.uma.data.database

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {
    @Query("SELECT * from characters ORDER BY name ASC")
    fun getAllCharacters(): Flow<List<CharacterEntity>>
}