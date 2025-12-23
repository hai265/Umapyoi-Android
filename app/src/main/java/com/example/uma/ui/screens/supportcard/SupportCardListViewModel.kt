package com.example.uma.ui.screens.supportcard

import com.example.uma.domain.GetSupportCardsWithCharacterNameUseCase
import com.example.uma.ui.screens.common.BaseListViewModel
import com.example.uma.ui.screens.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val TAG = "SupportCardListViewModel"
data class SupportCardListState(
    override val list : List<SupportCardListItem> = emptyList(),
    override val syncing: Boolean = false
): UiState<SupportCardListItem>

/*
 TODO Features
 1. Allow user to filter, sort (ascending, descending, filter based on some criteria
*/
@HiltViewModel
class SupportCardListViewModel @Inject constructor(
    private val getSupportCardsWithCharacterNameUseCase: GetSupportCardsWithCharacterNameUseCase
) : BaseListViewModel<SupportCardListItem, SupportCardListState>(initialState = SupportCardListState()) {
    // This is flow to eventually support sorting, etc

    init {
        start()
    }

    override fun getAllItems(): Flow<List<SupportCardListItem>> =
        getSupportCardsWithCharacterNameUseCase.invoke()

    override fun filterItems(
        searchTerm: String,
        items: List<SupportCardListItem>
    ): List<SupportCardListItem> {
        //Trim leading spaces
        val trimmedSearchTerm = searchTerm.trimStart()
        if (trimmedSearchTerm.isEmpty()) {
            return items
        }
        return items.filter { it.characterName.contains(searchTerm, ignoreCase = true) }
    }

    override suspend fun syncData() {
        getSupportCardsWithCharacterNameUseCase.sync()
    }

    override fun SupportCardListState.copy(
        list: List<SupportCardListItem>?,
        syncing: Boolean?
    ): SupportCardListState {
        return this.copy(
            list = list ?: this.list,
            syncing = syncing ?: this.syncing
        )
    }

    init {
        start()
    }
}