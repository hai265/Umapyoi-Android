package com.example.uma.data.repository

import android.util.Log
import com.example.uma.data.database.CharacterDao
import com.example.uma.data.database.CharacterEntity
import com.example.uma.data.network.NetworkCharacterDetails
import com.example.uma.data.network.NetworkListCharacter
import com.example.uma.data.network.UmaApiService
import com.example.uma.ui.screens.models.BasicCharacterInfo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import okio.IOException
import javax.inject.Inject

private const val TAG = "CharacterRepositoryImpl"

//TODO: This only calls network and writes to db when we run getAllCharacters
class CharacterRepositoryImpl @Inject constructor(
    private val umaApiService: UmaApiService,
    private val characterDao: CharacterDao,
) : CharacterRepository {

    override fun getAllCharacters(): Flow<List<BasicCharacterInfo>> = flow {
        val dbFlow = characterDao.getAllCharacters().map { dbCharacters ->
            dbCharacters.map { it.toUmaCharacter() }
        }
        emitAll(dbFlow)

        try {
            val result = umaApiService.getAllCharacters()
            characterDao.insertAllIgnoreExisting(result.map { it.toCharacterEntity() })
        } catch (e: IOException) {
            Log.e(TAG, "Error connecting $e")
        }

    }


    // Also get this from the db
    //TODO: Return DetailedCharacterInfo here?
    override fun getCharacterById(id: Int): Flow<BasicCharacterInfo?> = flow {
        val dbFlow = characterDao.getAllCharacters().map { character ->
            character.firstOrNull() { it.id == id }?.toUmaCharacter()
        }
        emitAll(dbFlow)

        try {
            val result = umaApiService.getCharacterById(id)
            characterDao.insertOrUpdate(result.toCharacterEntity())
        } catch (e: IOException) {
            Log.e(TAG, "Error connecting $e")
        }
    }
}

private fun NetworkCharacterDetails.toCharacterEntity(): CharacterEntity = CharacterEntity(
    id = id,
    name = nameEn,
    image = thumbImg,
    categoryLabelJp = categoryLabel ?: "",
    categoryLabelEn = categoryLabelEn ?: "",
    colorMain = colorMain ?: "",
    colorSub = colorSub ?: "",
)

private fun NetworkListCharacter.toCharacterEntity() = CharacterEntity(
    id = id,
    name = name,
    image = image,
    categoryLabelJp = categoryLabelJp,
    categoryLabelEn = categoryLabelEn,
    colorMain = colorMain,
    colorSub = colorSub,
)

private fun CharacterEntity.toUmaCharacter() = BasicCharacterInfo(
    id = id,
    name = name,
    image = image
)

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkUmaRepositoryModule {
    @Binds
    abstract fun bindsUmaRepository(repository: CharacterRepositoryImpl): CharacterRepository
}