package com.example.uma.ui.screens.supportcard

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.uma.domain.GetSupportCardDetailsWithCharacterNameUseCase
import com.example.uma.ui.UmaNavigables
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

sealed interface SupportCardDetailsScreenUiState {
    data class Success(val supportCard: SupportCardDetailsUiModel) : SupportCardDetailsScreenUiState
    object Loading : SupportCardDetailsScreenUiState
    data class Error(val error: String) : SupportCardDetailsScreenUiState
}

@HiltViewModel
class SupportCardDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getSupportCardDetailsWithCharacterNameUseCase: GetSupportCardDetailsWithCharacterNameUseCase
) : ViewModel() {
    private val supportCardId = savedStateHandle.toRoute<UmaNavigables.SupportCardDetails>().id
    private val _state: MutableStateFlow<SupportCardDetailsScreenUiState> = MutableStateFlow(
        SupportCardDetailsScreenUiState.Loading
    )
    val state: StateFlow<SupportCardDetailsScreenUiState> =
        getSupportCardDetailsWithCharacterNameUseCase
            .invoke(supportCardId)
            .map { supportCard ->
                SupportCardDetailsScreenUiState.Success(supportCard)
            }
            .stateIn(
                viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = SupportCardDetailsScreenUiState.Loading
            )
}