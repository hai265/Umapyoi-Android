package com.example.uma.data.network.supportcards

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkSupportCardBasic(
    @SerialName("chara_id") val characterId: Int,
    @SerialName("gametora") val gametoraPath: String,
    val id: Int,
    @SerialName("title_en") val titleEn: String?,
)
