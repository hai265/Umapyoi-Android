package com.example.uma.ui.screens.umalist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.uma.data.models.Character
import com.example.uma.data.models.SupportCardListItem
import com.example.uma.data.repository.character.CharacterRepository
import com.example.uma.domain.GetSupportCardsWithCharacterNameUseCase
import com.example.uma.ui.UmaNavigables
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


/*
* Modeled off of umapyoi character page https://umapyoi.net/character/admire-vega
* */
sealed interface CharacterScreenUiState {
    data class Success(val character: Character, val supportCards: List<SupportCardListItem>) :
        CharacterScreenUiState

    object Loading : CharacterScreenUiState
    data class Error(val error: String) : CharacterScreenUiState
}

@HiltViewModel()
class CharacterDetailsScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    umaRepo: CharacterRepository,
    getSupportCardsWithCharacterNameUseCase: GetSupportCardsWithCharacterNameUseCase,
) : ViewModel() {
    private val characterId = savedStateHandle.toRoute<UmaNavigables.Character>().id
    val state: StateFlow<CharacterScreenUiState> =
        umaRepo.getCharacterDetailsById(characterId)
            .map { character ->
                CharacterScreenUiState.Success(character, listOf())
            }
            .catch { e ->
                CharacterScreenUiState.Error("Error loading character $e")
            }
            .stateIn(
                viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = CharacterScreenUiState.Loading
            )

    //TODO: Don't remember why i did this oncompletion, can probably delete
}


