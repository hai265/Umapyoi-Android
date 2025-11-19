package com.example.uma.ui.screens.umalist

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uma.data.network.UmaRepository
import com.example.uma.ui.models.UmaCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UmaListState(
    val umaList: List<UmaCharacter> = listOf(),
)

/*
 TODO Features
 1. Allow user to filter, sort (ascending, descending, filter based on some criteria
*/
@HiltViewModel
class UmaListViewModel @Inject constructor(
    private val umaRepository: UmaRepository
) : ViewModel() {
    // This is flow to eventually support sorting, etc
    private val _umaList = MutableStateFlow(UmaListState())
    val umaList: StateFlow<UmaListState> = _umaList

    val gridState = LazyGridState()
    init {
        viewModelScope.launch {
            val characters = umaRepository.getAllCharacters()
            _umaList.value = UmaListState(characters)
        }
    }

    //TODO: Open uma character details page
    fun onClickUma() {

    }

}