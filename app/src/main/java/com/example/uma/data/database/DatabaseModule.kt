package com.example.uma.data.database

import android.content.Context
import androidx.room.Room
import com.example.uma.data.database.character.CharacterDao
import com.example.uma.data.database.character.CharacterDatabase
import com.example.uma.data.database.supportcard.SupportCardDao
import com.example.uma.data.database.supportcard.SupportCardDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DatabaseModule {
    companion object {
        @Provides
        fun providesCharacterDao(@ApplicationContext context: Context): CharacterDao {
            return Room.databaseBuilder(
                context,
                CharacterDatabase::class.java, "character_database"
            ).build().characterDao()
        }

        @Provides
        fun providesSupportCardDao(@ApplicationContext context: Context): SupportCardDao {
            return Room.databaseBuilder(
                context,
                SupportCardDatabase::class.java, "character_database"
            ).build().supportCardDao()
        }
    }
}