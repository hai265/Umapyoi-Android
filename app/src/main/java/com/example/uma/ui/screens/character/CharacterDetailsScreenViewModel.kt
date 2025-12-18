package com.example.uma.ui.screens.character

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.uma.data.models.CharacterDetailed
import com.example.uma.data.models.SupportCardBasic
import com.example.uma.data.repository.character.CharacterRepository
import com.example.uma.data.repository.supportcard.SupportCardRepository
import com.example.uma.ui.UmaNavigables
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


/*
* Modeled off of umapyoi character page https://umapyoi.net/character/admire-vega
* */
sealed interface CharacterScreenUiState {
    data class Success(
        val characterDetailed: CharacterDetailed,
        val supportCards: List<SupportCardBasic>
    ) :
        CharacterScreenUiState

    object Loading : CharacterScreenUiState
    data class Error(val error: String) : CharacterScreenUiState
}

@HiltViewModel()
class CharacterDetailsScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val characterRepo: CharacterRepository,
    //TODO: I want to show the support card types in the list (like in gametora) so might replace with a usecase
    // https://gametora.com/umamusume/characters/tamamo-cross
    supportCardRepository: SupportCardRepository,
) : ViewModel() {
    private val id = savedStateHandle.toRoute<UmaNavigables.Character>().id
    val state: StateFlow<CharacterScreenUiState> =
        characterRepo.getCharacterDetailsById(id)
            .map { character ->
                val supportCards =
                    supportCardRepository.getSupportCardsByCharacterId(character.characterBasic.gameId)
                CharacterScreenUiState.Success(character, supportCards)
            }
            .catch { e ->
                CharacterScreenUiState.Error("Error loading character $e")
            }
            .stateIn(
                viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = CharacterScreenUiState.Loading
            )

    init {
        viewModelScope.launch {
            characterRepo.syncCharacterDetails(id)
        }
    }

    fun onTapFavorite(isFavorite: Boolean) {
        if(state.value is CharacterScreenUiState.Success) {
            val id = (state.value as CharacterScreenUiState.Success).characterDetailed.characterBasic.id
            viewModelScope.launch {
                characterRepo.setCharacterFavoriteStatus(id, isFavorite)
            }
        }
    }
}


