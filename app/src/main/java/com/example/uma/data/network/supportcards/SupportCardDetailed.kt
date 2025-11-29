package com.example.uma.data.network.supportcards

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * https://umapyoi.net/api/v1/support/10001 for example
 * @param rarity 1 -> R, 2 -> SR, 3 -> SSR
 *
 * */
@Serializable
data class SupportCardDetailed(
    @SerialName("chara_id") val characterId: Int,
    @SerialName("gametora") val gametoraPath: String,
    val id: Int,
    val rarity: Int,
    @SerialName("rarity_string") val rarityString: String,
    @SerialName("start_date") val startDate: Int,
    @SerialName("title") val titleJp: String,
    @SerialName("title_en") val titleEn: String,
    @SerialName("type") val type: String,
    @SerialName("type_icon_url") val typeIconUrl: String,
)
