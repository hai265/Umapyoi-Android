package com.example.uma.ui.screens.common

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface UiState<T> {
    val list: List<T>
    val syncing: Boolean
}

//Base class used for CharacterList and SupportCardList viewmodels to consolidate filtering and syncing logic
//Asked gemini for this
abstract class BaseListViewModel<T, S : UiState<T>>(
    initialState: S
) : ViewModel() {
    val searchTextBoxState: TextFieldState = TextFieldState()

    private val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<S> = _uiState.asStateFlow()

    //We can't call getAllItems in the abstract class's init block since getAllItems isn't initialized yet
    //so we call it in the superclass's init
    fun start() {
        @OptIn(FlowPreview::class)
        snapshotFlow { searchTextBoxState.text }
            .debounce(300L)
            .combine(getAllItems()) { searchText, items ->
                _uiState.update { currentState: S ->
                    (currentState.copy(list = filterItems(searchText.toString(), items)))
                }
            }.launchIn(viewModelScope)
    }

    protected abstract fun getAllItems(): Flow<List<T>>

    /**
     * The logic to filter the list based on a search term.
     */
    protected abstract fun filterItems(searchTerm: String, items: List<T>): List<T>

    /**
     * The suspend function to trigger a data sync.
     */
    protected abstract suspend fun syncData()

    // --- Public methods for the UI to call ---

    fun refreshList() {
        _uiState.update {
            it.copy(syncing = true)
        }
        viewModelScope.launch {
            syncData()
            _uiState.update {
                it.copy(syncing = false)
            }
        }
    }

    // Helper to bypass reflection/casting issues with data class `copy`.
    // Concrete ViewModels must implement this.
    abstract fun S.copy(list: List<T>? = null, syncing: Boolean? = null): S
}