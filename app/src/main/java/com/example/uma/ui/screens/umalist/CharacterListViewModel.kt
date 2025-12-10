package com.example.uma.ui.screens.umalist

import android.util.Log
import com.example.uma.data.models.Character
import com.example.uma.data.repository.character.CharacterRepository
import com.example.uma.ui.screens.common.BaseListViewModel
import com.example.uma.ui.screens.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private const val TAG = "UmaListViewModel"

//TODO: Make this a sealed class so we can show blank screen, loading, normal screen
data class UmaListState(
    override val list: List<Character> = emptyList(),
    override val syncing: Boolean = false
) : UiState<Character>

/*
 TODO Features
 1. Allow user to filter, sort (ascending, descending, filter based on some criteria
*/
@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val characterRepository: CharacterRepository,
) : BaseListViewModel<Character, UmaListState>(initialState = UmaListState()) {

    init {
        start()
    }
    override fun getAllItems()  = characterRepository.getAllCharacters()

    override fun filterItems(
        searchTerm: String,
        items: List<Character>
    ): List<Character> {
        //Trim leading spaces
        val trimmedSearchTerm = searchTerm.trimStart()
        if (trimmedSearchTerm.isEmpty()) {
            return items
        }
        return items.filter { it.name.contains(searchTerm, ignoreCase = true) }
    }

    override suspend fun syncData() {
        Log.d(TAG, "refreshList triggered")
        characterRepository.sync()
    }

    override fun UmaListState.copy(
        list: List<Character>?,
        syncing: Boolean?
    ): UmaListState {
        return this.copy(
            list = list ?: this.list,
            syncing = syncing ?: this.syncing
        )
    }
}