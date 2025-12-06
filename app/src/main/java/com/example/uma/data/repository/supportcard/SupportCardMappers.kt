package com.example.uma.data.repository.supportcard

import com.example.uma.data.database.supportcard.SupportCardEntity
import com.example.uma.data.network.supportcards.NetworkSupportCardBasic
import com.example.uma.data.network.supportcards.NetworkSupportCardDetailed

private const val GAMETORA_IMAGE_URL =
    "https://gametora.com/images/umamusume/supports/tex_support_card"
/**
 * Constructs the full image URL for a given internal support card ID.
 *
 * @param cardId The internal identifier for the support card.
 * @return The complete, formatted URL string for the image.
 */
private fun buildGameToraImageUrl(cardId: Int): String {
    return "${GAMETORA_IMAGE_URL}_$cardId.png"
}

fun SupportCardEntity.toSupportCardBasic(): SupportCardBasic {
    return SupportCardBasic(
        id = id,
        characterId = characterId,
        imageUrl = buildGameToraImageUrl(id) // Use the builder function
    )
}

fun NetworkSupportCardBasic.toSupportCardEntity(): SupportCardEntity {
    return SupportCardEntity(
        id = id,
        characterId = characterId,
        titleEn = titleEn,
        gametoraPath = gametoraPath,
        rarity = null,
        dateAddedMs = null,
        titleJp = null,
        cardType = null,
        typeIconUrl = null
    )
}

fun NetworkSupportCardDetailed.toSupportCardEntity(): SupportCardEntity {
    return SupportCardEntity(
        id = id,
        characterId = characterId,
        titleEn = titleEn,
        gametoraPath = gametoraPath,
        rarity = CardRarity.fromInt(rarity),
        dateAddedMs = startDateSeconds.toMilliseconds(),
        titleJp = titleJp,
        cardType = CardType.fromString(type),
        typeIconUrl = typeIconUrl
    )
}

fun SupportCardEntity.toSupportCard(): SupportCardDetailed {
    return SupportCardDetailed(
        id = id,
        characterId = characterId,
        imageUrl = buildGameToraImageUrl(id),
        //TODO: Make SupportCardDetailed's fields nullable as well
        rarity = rarity,
        dateAddedMs = dateAddedMs,
        titleEn = titleEn,
        titleJp = titleJp,
        cardType = cardType,
        typeIconUrl = typeIconUrl
    )
}

private fun Int.toMilliseconds(): Long {
    return this * 1000L
}