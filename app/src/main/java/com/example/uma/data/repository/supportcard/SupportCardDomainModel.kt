package com.example.uma.data.repository.supportcard

data class SupportCard(
    val id: Int,
    val characterId: Int,
    val imageUrl: String,
    val rarity: CardRarity,
    val dateAdded: Long,
    val titleEn: String,
    val titleJp: String,
    val cardType: CardType,
    val typeIconUrl: String,
)

enum class CardRarity(val rank: Int) {
    UNKNOWN(0),
    R(1),
    SR(2),
    SSR(3);

    companion object {
        fun fromInt(num: Int): CardRarity =
            when (num) {
                1 -> R
                2 -> SR
                3 -> SSR
                else -> UNKNOWN
            }
    }
}

enum class CardType{
    UNKNOWN,
    SPEED,
    POWER,
    STAMINA,
    GUTS,
    WIT,
    PAL;


    companion object {
        fun fromString(typeString: String): CardType {
            val normalized = typeString.uppercase()
            return enumValues<CardType>().firstOrNull{ it.name == normalized } ?: UNKNOWN
        }

    }
}
