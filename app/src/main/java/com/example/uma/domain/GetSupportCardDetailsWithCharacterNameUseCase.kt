package com.example.uma.domain

import com.example.uma.data.repository.character.CharacterRepository
import com.example.uma.data.repository.supportcard.SupportCardRepository
import com.example.uma.ui.screens.supportcard.SupportCardDetailsUiModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSupportCardDetailsWithCharacterNameUseCase  @Inject constructor(
    private val supportCardRepository: SupportCardRepository,
    private val characterRepository: CharacterRepository,
) {
    operator fun invoke(supportCardId: Int): Flow<SupportCardDetailsUiModel> = TODO()
}