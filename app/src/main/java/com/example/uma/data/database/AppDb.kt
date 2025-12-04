package com.example.uma.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.uma.data.database.character.CharacterDao
import com.example.uma.data.database.character.CharacterDetailEntity
import com.example.uma.data.database.character.CharacterEntity
import com.example.uma.data.database.supportcard.SupportCardDao
import com.example.uma.data.database.supportcard.SupportCardEntity

@Database(entities = [CharacterEntity::class, CharacterDetailEntity::class, SupportCardEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun supportCardDao(): SupportCardDao
}