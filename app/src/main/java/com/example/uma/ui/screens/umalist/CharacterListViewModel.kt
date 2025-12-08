package com.example.uma.ui.screens.umalist

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uma.data.models.Character
import com.example.uma.data.repository.character.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

//TODO: Make this a sealed class so we can show blank screen, loading, normal screen
data class UmaListState(
    val umaList: List<Character>,
)

/*
 TODO Features
 1. Allow user to filter, sort (ascending, descending, filter based on some criteria
*/
@HiltViewModel
class UmaListViewModel @Inject constructor(
    characterRepository: CharacterRepository,
) : ViewModel() {
    // This is flow to eventually support sorting, etc
    val searchTextBoxState: TextFieldState = TextFieldState()

    @OptIn(FlowPreview::class)
    val umaListState: StateFlow<UmaListState> =
        snapshotFlow { searchTextBoxState.text }
            .debounce(300L)
            .combine(characterRepository.getAllCharacters()) { textField, characters ->
                textField.toString() to characters
            }
            .map { (searchText, characters) ->
                UmaListState(umaList = filterCharacters(searchText, characters))
            }
            .stateIn(
                viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = UmaListState(listOf())
            )

    private fun filterCharacters(searchTerm: String, characters: List<Character>): List<Character> {
        //Trim leading spaces
        val trimmedSearchTerm = searchTerm.trimStart()
        if (trimmedSearchTerm.isEmpty()) {
            return characters
        }
        return characters.filter { it.name.contains(searchTerm, ignoreCase = true) }
    }
}