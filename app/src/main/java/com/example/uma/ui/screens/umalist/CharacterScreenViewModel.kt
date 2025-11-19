package com.example.uma.ui.screens.umalist

import androidx.lifecycle.ViewModel
import com.example.uma.ui.models.UmaCharacter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


/*
* Modeled off of umapyoi character page https://umapyoi.net/character/admire-vega
* */
sealed interface CharacterUiState {
    data class Success(val umaCharacter: UmaCharacter): CharacterUiState
    object Loading: CharacterUiState
    data class Error(val error: String): CharacterUiState
    object Initial: CharacterUiState
}

class CharacterScreenViewModel: ViewModel() {

    var state: StateFlow<CharacterUiState> = MutableStateFlow(CharacterUiState.Initial)
        private set


}