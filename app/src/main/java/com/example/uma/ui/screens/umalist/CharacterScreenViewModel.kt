package com.example.uma.ui.screens.umalist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uma.data.network.UmaRepository
import com.example.uma.ui.models.UmaCharacter
import com.example.uma.ui.screens.randomgame.RandomUmaUiState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


/*
* Modeled off of umapyoi character page https://umapyoi.net/character/admire-vega
* */
sealed interface CharacterScreenUiState {
    data class Success(val umaCharacter: UmaCharacter): CharacterScreenUiState
    object Loading: CharacterScreenUiState
    data class Error(val error: String): CharacterScreenUiState
}

@HiltViewModel(assistedFactory = CharacterScreenViewModel.Factory::class)
class CharacterScreenViewModel @AssistedInject constructor(
    @Assisted private val characterId: Int,
    private val umaRepo: UmaRepository,
): ViewModel() {
    private val _state: MutableStateFlow<CharacterScreenUiState> = MutableStateFlow(CharacterScreenUiState.Loading)
    val state: StateFlow<CharacterScreenUiState> = _state

    init {
        viewModelScope.launch {
            umaRepo.getCharacterById(characterId).collect { character ->
                _state.value = CharacterScreenUiState.Success(character)
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(characterId: Int): CharacterScreenViewModel
    }
}


