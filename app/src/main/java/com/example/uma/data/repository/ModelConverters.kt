package com.example.uma.data.repository

import com.example.uma.data.database.CharacterEntity
import com.example.uma.data.network.NetworkUmaCharacter

fun NetworkUmaCharacter.toUmaCharacter() = ListCharacter(
    id = id,
    name = name,
    image = image
)

fun CharacterEntity.toUmaCharacter() = ListCharacter(
    id = id,
    name = name,
    image = image
)

fun NetworkUmaCharacter.toCharacterEntity() = CharacterEntity(
    id = id,
    name = name,
    image = image,
    categoryLabelJp = categoryLabelJp,
    categoryLabelEn = categoryLabelEn,
    colorMain = colorMain,
    colorSub = colorSub,
)
