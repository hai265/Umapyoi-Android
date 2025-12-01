package com.example.uma.data.database.character

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CharacterDao {
    @Query("SELECT * from characters ORDER BY name ASC")
    suspend fun getAllCharacters(): List<CharacterEntity>
//    @Query("SELECT * from characters WHERE id = :id")
//    suspend fun getCharacterById(id: Int): CharacterEntity

    @Query("SELECT * from characterDetails WHERE id = :id")
    suspend fun getCharacterDetailsById(id: Int): CharacterDetailEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllIgnoreExisting(characters: List<CharacterEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateOrInsertCharacterDetail(character: CharacterDetailEntity)
}