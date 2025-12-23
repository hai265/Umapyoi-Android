package com.example.uma.domain

import com.example.uma.data.repository.character.CharacterRepository
import com.example.uma.data.repository.supportcard.SupportCardRepository
import com.example.uma.ui.screens.supportcard.SupportCardDetailsUiModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

//Gut instinct tells me that the support card name should be in the support card domain model
//but since the api doesn't include a name I can't include it
class GetSupportCardDetailsWithCharacterNameUseCase @Inject constructor(
    private val supportCardRepository: SupportCardRepository,
    private val characterRepository: CharacterRepository,
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(supportCardId: Int): Flow<SupportCardDetailsUiModel> =
        supportCardRepository
            .getSupportCardById(supportCardId)
            .flatMapLatest { supportCard ->
                characterRepository.getCharacterDetailsByCharacterId(supportCard.characterId).map { character ->
                    SupportCardDetailsUiModel(
                        details = supportCard,
                        characterName = character.characterBasic.name
                    )
                }
            }
}