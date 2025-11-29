package com.example.uma.ui.screens.supportcard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uma.data.repository.supportcard.SupportCardRepository
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

//TODO: Try getting id from savestatebundle?
@HiltViewModel
class SupportCardDetailsViewModel @Inject constructor(
    private val supportCardRepo: SupportCardRepository
) : ViewModel() {
    //TODO: Delete
    private val testId = 0

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
                            testId
                        )
                    )
            } catch (e: Throwable) {
                _state.value = SupportCardDetailsScreenUiState.Error(e.toString())
            }

        }
    }
}