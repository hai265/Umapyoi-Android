package com.example.uma.ui.screens.umalist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uma.data.models.Character
import com.example.uma.data.repository.character.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

//TODO: Make this a sealed class so we can show blank screen, loading, normal screen
data class UmaListState(
    val umaList: List<Character> = listOf(),
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
    val umaList: StateFlow<UmaListState> = characterRepository.getAllCharacters()
        .map { characters ->
            UmaListState(umaList = characters)
        }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = UmaListState()
        )
}