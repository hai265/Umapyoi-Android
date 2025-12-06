package com.example.uma.domain

import com.example.uma.data.models.SupportCardListItem
import com.example.uma.data.repository.character.CharacterRepository
import com.example.uma.data.repository.supportcard.SupportCardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetSupportCardsWithCharacterNameUseCase @Inject constructor(
    private val supportCardRepository: SupportCardRepository,
    private val characterRepository: CharacterRepository,
) {
    operator fun invoke(): Flow<List<SupportCardListItem>> =
        characterRepository.getAllCharacters()
            .combine(supportCardRepository.getAllSupportCards()) { characters, supportCards ->
                //TODO: Support cards from Akikawa not shown (since has null gameId)
                val charMap = characters
                    .filter { it.gameId != null }
                    .associateBy { it.gameId }
                supportCards.map { card ->
                    val character = charMap[card.characterId]
                    SupportCardListItem(
                        id = card.id,
                        characterId = character?.gameId ?: 0,
                        imageUrl = card.imageUrl,
                        characterName = character?.name ?: "",
                    )
                }
            }
}
