package com.example.uma.data.network.supportcards

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// https://umapyoi.net/api/v1/support
// Ideally we'd like the character name in here but api doesn't provide it
@Serializable
data class NetworkSupportCardBasic(
    @SerialName("chara_id") val characterId: Int,
    @SerialName("gametora") val gametoraPath: String,
    val id: Int,
    @SerialName("title_en") val titleEn: String?,
)
