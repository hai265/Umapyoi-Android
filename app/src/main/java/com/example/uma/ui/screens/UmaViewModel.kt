package com.example.uma.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uma.data.UmaRepository
import com.example.uma.ui.models.UmaCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

sealed interface UmaUiState {
    data class Success(val umaCharacter: UmaCharacter): UmaUiState
    object Loading: UmaUiState
    object Error: UmaUiState
    object Initial: UmaUiState
}

@HiltViewModel
class UmaViewModel @Inject constructor(
    private val umaRepository: UmaRepository
) : ViewModel() {
    var umaUiState: UmaUiState by mutableStateOf(UmaUiState.Loading)
        private set

    init {
        umaUiState = UmaUiState.Initial
    }

    fun getRandomUma() : Unit {
//        TODO: set currentUma to a random uma
        umaUiState = UmaUiState.Loading

        viewModelScope.launch {
            try {
                val allCharacters = umaRepository.getAllCharacters()
                val randomUma = allCharacters.random()
                umaUiState = UmaUiState.Success(randomUma)
            } catch (e: IOException) {

            }
        }
    }
}