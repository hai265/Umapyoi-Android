package com.example.uma.data.repository

import com.example.uma.data.repository.character.CharacterRepository
import com.example.uma.data.repository.character.CharacterRepositoryImpl
import com.example.uma.data.repository.supportcard.SupportCardRepository
import com.example.uma.data.repository.supportcard.SupportCardRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindsCharacterRepository(repository: CharacterRepositoryImpl): CharacterRepository

    @Binds
    abstract fun bindsSupportCardRepository(repository: SupportCardRepositoryImpl): SupportCardRepository
}