package com.example.uma.ui.screens.umalist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.uma.data.models.Character
import com.example.uma.data.repository.character.CharacterRepository
import com.example.uma.ui.UmaNavigables
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


/*
* Modeled off of umapyoi character page https://umapyoi.net/character/admire-vega
* */
sealed interface CharacterScreenUiState {
    data class Success(val character: Character): CharacterScreenUiState
    object Loading: CharacterScreenUiState
    data class Error(val error: String): CharacterScreenUiState
}

@HiltViewModel()
class CharacterDetailsScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val umaRepo: CharacterRepository,
): ViewModel() {
    private val characterId = savedStateHandle.toRoute<UmaNavigables.Character>().id
    private val _state: MutableStateFlow<CharacterScreenUiState> = MutableStateFlow(CharacterScreenUiState.Loading)
    val state: StateFlow<CharacterScreenUiState> = _state

    init {
        viewModelScope.launch {
            var emitted = false
            umaRepo.getCharacterDetailsById(characterId)
                .onEach { character ->
                    _state.value = CharacterScreenUiState.Success(character)
                    emitted = true
                }
                .onCompletion { cause ->
                    if (cause != null) {
                        return@onCompletion
                    }
                    if (!emitted) {
                        _state.value = CharacterScreenUiState.Error("No characters were loaded")
                    }
                }
                .catch { e ->
                    _state.value = CharacterScreenUiState.Error("Error retrieving character $e")
                }
                .collect()
        }
    }
}


