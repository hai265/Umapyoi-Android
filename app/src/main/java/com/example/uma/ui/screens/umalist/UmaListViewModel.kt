package com.example.uma.ui.screens.umalist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uma.data.repository.character.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
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
    private val characterRepository: CharacterRepository
) : ViewModel() {
    // This is flow to eventually support sorting, etc
    private val _umaList = MutableStateFlow(UmaListState())
    val umaList: StateFlow<UmaListState> = _umaList

    init {
        viewModelScope.launch {
            characterRepository.getAllCharacters().collect { characters ->
                _umaList.value = UmaListState(characters)
            }
        }
    }
}