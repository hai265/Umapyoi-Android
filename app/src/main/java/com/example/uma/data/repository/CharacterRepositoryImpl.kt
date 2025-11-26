package com.example.uma.data.repository

import android.util.Log
import com.example.uma.data.database.character.CharacterDao
import com.example.uma.data.database.character.CharacterDetailEntity
import com.example.uma.data.database.character.CharacterEntity
import com.example.uma.data.network.NetworkCharacterDetails
import com.example.uma.data.network.NetworkListCharacter
import com.example.uma.data.network.UmaApiService
import com.example.uma.ui.screens.models.BasicCharacterInfo
import com.example.uma.ui.screens.models.DetailedCharacterInfo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import okio.IOException
import javax.inject.Inject

private const val TAG = "CharacterRepositoryImpl"

//TODO: This only calls network and writes to db when we run getAllCharacters
class CharacterRepositoryImpl @Inject constructor(
    private val umaApiService: UmaApiService,
    private val characterDao: CharacterDao,
) : CharacterRepository {

    override fun getAllCharacters(): Flow<List<BasicCharacterInfo>> = flow {
        emit(characterDao.getAllCharacters().map{it.toUmaCharacter()})
        try {
            val characters = umaApiService.getAllCharacters().map { it.toCharacterEntity() }
            characterDao.insertAllIgnoreExisting(characters)
            emit(characters.map { it.toUmaCharacter() })
        } catch (e: IOException) {
            Log.e(TAG, "Error connecting $e")
        }
    }

    override fun getCharacterDetailsById(id: Int): Flow<DetailedCharacterInfo> = flow {
        val starter = characterDao.getAllCharacters().first { it.id == id }
        emit (DetailedCharacterInfo(
            name = starter.name,
            birthDay = 0,
            birthMonth = 0,
            category = "",
            thumbImg = starter.image,
            slogan = "",
        ))
        characterDao.getCharacterDetailsById(id)?.let {emit (it.toDetailedCharacter())}
        try {
            val result = umaApiService.getCharacterById(id)
            characterDao.updateOrInsertCharacterDetail(result.toDetailedCharacterEntity())
            emit(result.toDetailedCharacter())
        } catch (e: IOException) {
            Log.e(TAG, "Error connecting $e")
        }
    }
}

private fun NetworkCharacterDetails.toDetailedCharacterEntity(): CharacterDetailEntity =
    CharacterDetailEntity(
        id = id,
        nameEn = nameEn,
        thumbImg = thumbImg,
        category = categoryLabelEn,
        colorMain = colorMain,
        colorSub = colorSub,
        date = dateGmt,
        slogan = slogan,
        birthDay = null,
        birthMonth = null,
        dateGmt = null,
        modifiedGmt = null,
        detailImgPc = null,
        detailImgSp = null,
        earsFact = null,
        familyFact = null,
        gameId = null,
        grade = null,
        height = null,
        link = null,
        nameEnInternal = null,
        nameJp = null,
        profile = null,
        residence = null,
        shoeSize = null,
        sizeB = null,
        sizeH = null,
        sizeW = null,
        snsIcon = null,
        strengths = null,
        tailFact = null,
        voice = null,
        weaknesses = null,
        weight = null,
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

private fun NetworkCharacterDetails.toDetailedCharacter(): DetailedCharacterInfo {
    return DetailedCharacterInfo(
        birthDay = birthDay,
        birthMonth = birthMonth,
        category = categoryLabelEn,
        name = nameEn,
        thumbImg = thumbImg,
        slogan = slogan ?: "",
    )
}

private fun CharacterDetailEntity.toDetailedCharacter(): DetailedCharacterInfo {
    return DetailedCharacterInfo(
        birthDay = birthDay,
        birthMonth = birthMonth,
        category = category,
        name = nameEn,
        thumbImg = thumbImg ?: "",
        slogan = slogan ?: "",
    )
}

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkUmaRepositoryModule {
    @Binds
    abstract fun bindsUmaRepository(repository: CharacterRepositoryImpl): CharacterRepository
}