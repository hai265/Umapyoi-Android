package com.example.uma.ui.screens.umalist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.uma.data.network.UmaRepository
import com.example.uma.ui.models.UmaCharacter
import com.example.uma.ui.screens.randomgame.RandomUmaUiState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


/*
* Modeled off of umapyoi character page https://umapyoi.net/character/admire-vega
* */
sealed interface CharacterScreenUiState {
    data class Success(val umaCharacter: UmaCharacter): CharacterScreenUiState
    object Loading: CharacterScreenUiState
    data class Error(val error: String): CharacterScreenUiState
    object Initial: CharacterScreenUiState
}

@HiltViewModel(assistedFactory = CharacterScreenViewModel.Factory::class)
class CharacterScreenViewModel @AssistedInject constructor(
    @Assisted private val characterId: Int,
    private val umaRepo: UmaRepository,
): ViewModel() {
    var state: StateFlow<CharacterScreenUiState> = MutableStateFlow(CharacterScreenUiState.Initial)
        private set

    @AssistedFactory
    interface Factory {
        fun create(characterId: Int): CharacterScreenViewModel
    }
}


