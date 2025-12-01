package com.example.uma.ui.screens.supportcard

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.uma.data.repository.supportcard.SupportCardRepository
import com.example.uma.ui.UmaNavigables
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface SupportCardDetailsScreenUiState {
    data class Success(val supportCard: SupportCard) : SupportCardDetailsScreenUiState
    object Loading : SupportCardDetailsScreenUiState
    data class Error(val error: String) : SupportCardDetailsScreenUiState
}

@HiltViewModel
class SupportCardDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val supportCardRepo: SupportCardRepository
) : ViewModel() {
    private val supportCardId = savedStateHandle.toRoute<UmaNavigables.SupportCardDetails>().id
    private val _state: MutableStateFlow<SupportCardDetailsScreenUiState> = MutableStateFlow(
        SupportCardDetailsScreenUiState.Loading
    )
    val state: StateFlow<SupportCardDetailsScreenUiState> = _state

    init {
        viewModelScope.launch {
            try {
                _state.value =
                    SupportCardDetailsScreenUiState.Success(
                        supportCardRepo.getSupportCardById(
                            supportCardId
                        )
                    )
            } catch (e: Throwable) {
                _state.value = SupportCardDetailsScreenUiState.Error(e.toString())
            }

        }
    }
}