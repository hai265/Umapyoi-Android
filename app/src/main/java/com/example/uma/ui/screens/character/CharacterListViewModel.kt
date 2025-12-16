package com.example.uma.ui.screens.character

import android.util.Log
import com.example.uma.data.models.CharacterBasic
import com.example.uma.data.repository.character.CharacterRepository
import com.example.uma.ui.screens.common.BaseListViewModel
import com.example.uma.ui.screens.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private const val TAG = "CharacterListViewModel"

//TODO: Make this a sealed class so we can show blank screen, loading, normal screen
data class CharacterListState(
    override val list: List<CharacterBasic> = emptyList(),
    override val syncing: Boolean = false
) : UiState<CharacterBasic>

/*
 TODO Features
 1. Allow user to filter, sort (ascending, descending, filter based on some criteria
*/
@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val characterRepository: CharacterRepository,
) : BaseListViewModel<CharacterBasic, CharacterListState>(initialState = CharacterListState()) {

    init {
        start()
    }
    override fun getAllItems()  = characterRepository.getAllCharacters()

    override fun filterItems(
        searchTerm: String,
        items: List<CharacterBasic>
    ): List<CharacterBasic> {
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

    override fun CharacterListState.copy(
        list: List<CharacterBasic>?,
        syncing: Boolean?
    ): CharacterListState {
        return this.copy(
            list = list ?: this.list,
            syncing = syncing ?: this.syncing
        )
    }
}