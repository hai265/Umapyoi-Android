package com.example.uma.data.repository

import com.example.uma.data.database.CharacterEntity
import com.example.uma.data.network.NetworkUmaCharacter

fun NetworkUmaCharacter.toUmaCharacter() = UmaCharacter(
    id = id,
    name = name,
    image = image
)

fun CharacterEntity.toUmaCharacter() = UmaCharacter(
    id = id,
    name = name,
    image = image
)

fun NetworkUmaCharacter.toCharacterEntity() = CharacterEntity(
    id = id,
    name = name,
    image = image
)
