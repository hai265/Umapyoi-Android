package com.example.uma.ui.screens.supportcard


data class SupportCardListItem(
    val id: Int,
    val characterId: Int,
    val imageUrl: String,
    val title: String,
)

data class SupportCard(
    val id: Int,
    val characterId: Int,
    val imageUrl: String,
    val rarity: CardRarity,
    val dateAdded: Long,
    val titleEn: String,
    val titleJp: String,
    val type: Type,
    val typeIconUrl: String,
)

enum class CardRarity {
    R,
    SR,
    SSR
}

enum class Type {
    SPEED,
    POWER,
    STAMINA,
    GUTS,
    WIT
}
