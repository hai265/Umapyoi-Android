package com.example.uma.domain

import com.example.uma.data.repository.character.CharacterRepository
import com.example.uma.data.repository.supportcard.SupportCardRepository
import com.example.uma.ui.screens.supportcard.SupportCardListItem
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
                val charMap = characters.associateBy { it.id }
                supportCards.map { card ->
                    val character = charMap[card.characterId]
                    SupportCardListItem(
                        id = card.id,
                        characterId = character?.id ?: 0,
                        imageUrl = card.imageUrl,
                        characterName = character?.name ?: "",
                    )
                }
            }
}
