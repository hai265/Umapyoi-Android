package com.example.uma.fakes.network

import com.example.uma.data.network.character.NetworkCharacterDetails
import com.example.uma.data.network.character.NetworkListCharacter

val fakeNetworkCharacterList = listOf(
    NetworkListCharacter(
        id = 1, gameId = 1, nameEn = "Special Week", image = "",
        nameJp = "スペシャルウィーク",
        categoryLabelJp = "ウマ",
        categoryLabelEn = "Uma",
        colorMain = "",
        colorSub = ""
    ),
    NetworkListCharacter(
        id = 2, gameId = 2, nameEn = "Tokai Teio", image = "",
        nameJp = "トウカイテイオー",
        categoryLabelJp = "ウマ",
        categoryLabelEn = "Uma",
        colorMain = "",
        colorSub = ""
    )
)

val fakeNetworkCharacterDetails = NetworkCharacterDetails(
    id = 1,
    gameId = 1001,
    nameEn = "Special Week",
    nameJp = "スペシャルウィーク",
    nameEnInternal = "special_week",
    birthDay = 1,
    birthMonth = 5,
    categoryLabelJp = "ウマ娘",
    categoryLabelEn = "Uma Musume",
    categoryValue = "character",
    colorMain = "#FF5252",
    colorSub = "#FFCDD2",
    dateGmt = "2022-01-01T12:00:00Z",
    modifiedGmt = "2022-01-01T12:00:00Z",
    detailImgPc = "https://example.com/special_week_pc.png",
    detailImgSp = "https://example.com/special_week_sp.png",
    thumbImg = "https://example.com/special_week_thumb.png",
    earsFact = "Her ears twitch when she's excited.",
    familyFact = "Raised by her human mother in Hokkaido.",
    grade = "2nd Year",
    height = 158,
    link = "https://umamusume.jp/character/specialweek",
    preferredUrl = "special-week",
    profile = "A cheerful and earnest girl from the countryside who dreams of becoming the best horse girl in Japan.",
    residence = "Tracen Academy Dorms",
    rowNumber = 1,
    shoeSize = "23.5",
    sizeB = 80,
    sizeH = 82,
    sizeW = 55,
    slogan = "I'll be the number one horse girl in Japan!",
    snsHeader = "https://example.com/special_week_header.png",
    snsIcon = "https://example.com/special_week_sns.png",
    strengths = "Stamina and Guts",
    tailFact = "Her tail wags uncontrollably when she eats carrots.",
    voiceUrl = "https://example.com/special_week_voice.mp3",
    weaknesses = "Can be a bit naive.",
    weight = "Slightly decreasing"
)
