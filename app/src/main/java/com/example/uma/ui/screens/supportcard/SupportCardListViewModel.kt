package com.example.uma.ui.screens.supportcard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uma.data.repository.supportcard.SupportCardRepository
import com.example.uma.domain.GetSupportCardsWithCharacterNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SupportCardListState(
    val suportCardList: List<SupportCardListItem> = listOf(),
)

/*
 TODO Features
 1. Allow user to filter, sort (ascending, descending, filter based on some criteria
*/
@HiltViewModel
class SupportCardListViewModel @Inject constructor(
    private val getSupportCardsWithCharacterNameUseCase: GetSupportCardsWithCharacterNameUseCase
) : ViewModel() {
    // This is flow to eventually support sorting, etc
    private val _supportCardList = MutableStateFlow(SupportCardListState())
    val supportCardList: StateFlow<SupportCardListState> = _supportCardList

    init {
        viewModelScope.launch {
            getSupportCardsWithCharacterNameUseCase.invoke().collect { supportCards ->
                _supportCardList.value = SupportCardListState(supportCards)
            }
        }
    }
}