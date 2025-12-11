package com.example.uma.data.network.character

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// https://umapyoi.net/api/v1/character/info
@Serializable
data class NetworkListCharacter(
    val id: Int,
//    I would make this non null but the api returns null for Akikawa (obj 157)
    @SerialName("game_id")
    val gameId: Int?,
    @SerialName("name_en")
    val nameEn: String,
    @SerialName("thumb_img")
    val image: String,
    @SerialName("name_jp")
    val nameJp: String,
    @SerialName("category_label")
    val categoryLabelJp: String,
    @SerialName("category_label_en")
    val categoryLabelEn: String,
    @SerialName("color_main")
    val colorMain: String,
    @SerialName("color_sub")
    val colorSub: String,
)