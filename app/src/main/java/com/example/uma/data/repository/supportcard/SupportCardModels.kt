package com.example.uma.data.repository.supportcard

data class SupportCardBasic(
    val id: Int,
    val characterId: Int,
    val imageUrl: String,
)

data class SupportCardDetailed(
    val id: Int,
    val characterId: Int,
    val imageUrl: String,
    val rarity: CardRarity?,
    val dateAdded: Long?,
    val titleEn: String?,
    val titleJp: String?,
    val cardType: CardType?,
    val typeIconUrl: String?,
)