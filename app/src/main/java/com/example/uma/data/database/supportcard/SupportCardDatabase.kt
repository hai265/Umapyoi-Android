package com.example.uma.data.database.supportcard

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SupportCardEntity::class], version = 1)
abstract class SupportCardDatabase : RoomDatabase() {
    abstract fun supportCardDao(): SupportCardDao
}