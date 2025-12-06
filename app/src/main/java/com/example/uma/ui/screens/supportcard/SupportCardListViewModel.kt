package com.example.uma.ui.screens.supportcard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uma.domain.SupportCardListItem
import com.example.uma.domain.GetSupportCardsWithCharacterNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "SupportCardListViewModel"
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
            getSupportCardsWithCharacterNameUseCase.invoke()
                .catch { e ->
                    Log.i(TAG, "Error calling getSupportCardsWithCharacterNameUseCase ")
                }
                .collect { supportCards ->
                _supportCardList.value = SupportCardListState(supportCards)
            }
        }
    }
}