package com.example.uma.data.repository.character

import com.example.uma.data.database.character.CharacterEntity
import com.example.uma.data.models.CharacterBasic
import com.example.uma.data.models.CharacterDetailed
import com.example.uma.data.models.CharacterProfile

fun CharacterEntity.toCharacterBasic(): CharacterBasic = CharacterBasic(
    id = id,
    gameId = characterId,
    name = nameEn,
    image = image,
    colorMain = colorMain,
    colorSub = colorSub,
    isFavorite = isFavorite
)

fun CharacterEntity.toCharacterDetailed(): CharacterDetailed =
    CharacterDetailed(
        characterBasic = CharacterBasic(
            id = id,
            gameId = characterId,
            name = nameEn,
            image = image,
            colorMain = colorMain,
            colorSub = colorSub,
            isFavorite = isFavorite
        ),
        characterProfile = CharacterProfile(
            slogan = slogan,
            category = categoryLabelEn,
            voice = voiceUrl,
        )
    )
