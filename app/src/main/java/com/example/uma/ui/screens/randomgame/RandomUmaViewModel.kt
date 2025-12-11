package com.example.uma.ui.screens.randomgame

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uma.data.repository.character.CharacterRepository
import com.example.uma.data.models.CharacterBasic
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

sealed interface RandomUmaUiState {
    data class Success(val characterBasic: CharacterBasic): RandomUmaUiState
    object Loading: RandomUmaUiState
    data class Error(val error: String): RandomUmaUiState
    object Initial: RandomUmaUiState
}

@HiltViewModel
class RandomUmaViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : ViewModel() {
    var randomUmaUiState: RandomUmaUiState by mutableStateOf(RandomUmaUiState.Loading)
        private set

    init {
        randomUmaUiState = RandomUmaUiState.Initial
    }

    fun getRandomUma() : Unit {
       // Only show loading on first button press
        if (randomUmaUiState == RandomUmaUiState.Initial) {
            randomUmaUiState = RandomUmaUiState.Loading
        }
        viewModelScope.launch {
            characterRepository.getAllCharacters().collect { allCharacters ->
                try {
                    val randomUma = allCharacters.random()
                    randomUmaUiState = RandomUmaUiState.Success(randomUma)
                } catch (e: IOException) {
                    randomUmaUiState = RandomUmaUiState.Error("Error: $e")
                }
            }
        }
    }
}