package com.example.uma.fakes

import com.example.uma.data.models.CharacterBasic
import com.example.uma.data.models.CharacterDetailed
import com.example.uma.data.models.CharacterProfile

val fakeCharacterBasic = CharacterBasic(
    id = 1,
    gameId = 1,
    name = "Special Week",
    image = "image",
    colorMain = "colorMain",
    colorSub = "colorSub",
    isFavorite = false
)

val fakeCharacterDetailed: CharacterDetailed = CharacterDetailed(
    characterBasic = fakeCharacterBasic,
    characterProfile = CharacterProfile(
        "slogan",
        "category"
    )
)