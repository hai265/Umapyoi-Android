package com.example.uma.data.database.character

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CharacterEntity::class, CharacterDetailEntity::class], version = 1)
abstract class CharacterDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}