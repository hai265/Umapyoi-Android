package com.example.uma.data.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkUmaCharacter(
    val id: Int,
    @SerialName("name_en") val name: String,
    @SerialName("thumb_img") val image: String,
)