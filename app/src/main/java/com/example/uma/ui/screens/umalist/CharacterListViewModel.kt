package com.example.uma.ui.screens.umalist

import android.util.Log
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uma.data.models.Character
import com.example.uma.data.repository.character.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "UmaListViewModel"

//TODO: Make this a sealed class so we can show blank screen, loading, normal screen
data class UmaListState(
    val umaList: List<Character>,
    val syncing: Boolean = false
)

/*
 TODO Features
 1. Allow user to filter, sort (ascending, descending, filter based on some criteria
*/
@HiltViewModel
class UmaListViewModel @Inject constructor(
    private val characterRepository: CharacterRepository,
) : ViewModel() {
    // This is flow to eventually support sorting, etc
    val searchTextBoxState: TextFieldState = TextFieldState()

    private val _umaListState = MutableStateFlow(
        UmaListState(
            listOf(),
            false
        )
    )
    val umaListState: StateFlow<UmaListState> = _umaListState.asStateFlow()


    init {
        @OptIn(FlowPreview::class)
        snapshotFlow { searchTextBoxState.text }
            .debounce(300L)
            .combine(characterRepository.getAllCharacters()) { searchText, characters ->
                _umaListState.update { currentState ->
                    currentState.copy(
                        umaList = filterCharacters(searchText.toString(), characters)
                    )
                }
            }.launchIn(viewModelScope)
    }


    private fun filterCharacters(searchTerm: String, characters: List<Character>): List<Character> {
        //Trim leading spaces
        val trimmedSearchTerm = searchTerm.trimStart()
        if (trimmedSearchTerm.isEmpty()) {
            return characters
        }
        return characters.filter { it.name.contains(searchTerm, ignoreCase = true) }
    }

    fun refreshList() {
        Log.d(TAG, "refreshList triggered")
        _umaListState.update { it.copy(syncing = true) }
        viewModelScope.launch {
            characterRepository.sync()
            _umaListState.update { it.copy(syncing = false) }
        }
    }
}