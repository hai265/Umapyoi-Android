package com.example.uma.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.uma.data.database.character.CharacterDao
import com.example.uma.data.database.supportcard.SupportCardDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DatabaseModule {
    companion object {
        @Provides
        @Singleton
        fun providesAppDatabase(@ApplicationContext context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java, "app_database"
            ).build()
        }

        @Provides
        fun providesCharacterDao(database: AppDatabase) = database.characterDao()

        @Provides
        fun providesSupportCardDao(database: AppDatabase) = database.supportCardDao()
    }
}