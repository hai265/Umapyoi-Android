package com.example.uma.data.repository.character

import com.example.uma.data.database.character.CharacterEntity
import com.example.uma.data.models.CharacterBasic
import com.example.uma.data.models.CharacterDetailed
import com.example.uma.data.models.CharacterProfile
import com.example.uma.data.network.character.NetworkCharacterDetails

fun CharacterEntity.toCharacterBasic(): CharacterBasic = CharacterBasic(
    id = id,
    gameId = gameId,
    name = nameEn,
    image = image,
    colorMain = colorMain,
    colorSub = colorSub
)

fun CharacterEntity.toCharacterDetailed(): CharacterDetailed =
    CharacterDetailed(
        characterBasic = CharacterBasic(
            id = id,
            gameId = gameId,
            name = nameEn,
            image = image,
            colorMain = colorMain,
            colorSub = colorSub
        ),
        characterProfile = CharacterProfile(
            slogan = slogan,
            category = categoryLabelEn
        )
    )

fun NetworkCharacterDetails.toCharacterDetailed(): CharacterDetailed = CharacterDetailed(
    characterBasic = CharacterBasic(
        id = id,
        gameId = gameId,
        name = nameEn,
        image = thumbImg,
        colorMain = colorMain,
        colorSub = colorSub
    ),
    characterProfile = CharacterProfile(
        slogan = slogan,
        category = categoryLabelEn
    )
)

