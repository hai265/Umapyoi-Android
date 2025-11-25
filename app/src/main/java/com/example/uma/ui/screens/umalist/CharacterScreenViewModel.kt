package com.example.uma.ui.screens.umalist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uma.data.repository.CharacterRepository
import com.example.uma.ui.screens.models.BasicCharacterInfo
import com.example.uma.ui.screens.models.DetailedCharacterInfo
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


/*
* Modeled off of umapyoi character page https://umapyoi.net/character/admire-vega
* */
sealed interface CharacterScreenUiState {
    data class Success(val detailedCharacterInfo: DetailedCharacterInfo): CharacterScreenUiState
    object Loading: CharacterScreenUiState
    data class Error(val error: String): CharacterScreenUiState
}

@HiltViewModel(assistedFactory = CharacterScreenViewModel.Factory::class)
class CharacterScreenViewModel @AssistedInject constructor(
    @Assisted private val characterId: Int,
    private val umaRepo: CharacterRepository,
): ViewModel() {
    private val _state: MutableStateFlow<CharacterScreenUiState> = MutableStateFlow(CharacterScreenUiState.Loading)
    val state: StateFlow<CharacterScreenUiState> = _state

    init {
        viewModelScope.launch {
            try {
                umaRepo.getCharacterById(characterId).collect { character ->
                    _state.value = CharacterScreenUiState.Success(character)
                }
            }
            catch (e: Throwable) {
                _state.value = CharacterScreenUiState.Error("Error retrieving character $e")
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(characterId: Int): CharacterScreenViewModel
    }
}


