package com.example.uma.data.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkUmaCharacter(
    val id: Int,
    @SerialName("name_en")
    val name: String,
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