package com.example.uma.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.uma.R
import com.example.uma.ui.models.UmaCharacter

sealed interface UmaUiState {
    data class Success(val umaCharacter: UmaCharacter): UmaUiState
    object Loading: UmaUiState
    object Error: UmaUiState
}

class UmaViewModel : ViewModel() {
    var umaUiState: UmaUiState by mutableStateOf(UmaUiState.Loading)
        private set

    init {
        umaUiState = UmaUiState.Success(UmaCharacter("Special Week", R.drawable.specialweek_icon))
    }

    fun getRandomUma() : Unit {
//        TODO: set currentUma to a random uma
    }
}