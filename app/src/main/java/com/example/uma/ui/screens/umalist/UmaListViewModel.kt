package com.example.uma.ui.screens.umalist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uma.data.network.UmaRepository
import com.example.uma.ui.models.UmaCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.MutableStateFlow

data class UmaListState(val umaList: List<UmaCharacter> = listOf())
/*
 TODO Features
 1. Allow user to filter, sort (ascending, descending, filter based on some criteria
*/
@HiltViewModel
class UmaListViewModel @Inject constructor(
    private val umaRepository: UmaRepository
): ViewModel() {
    // This is flow to eventually support sorting, etc
    private val _umaList = MutableStateFlow(UmaListState())
    val umaList: StateFlow<UmaListState> = _umaList

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