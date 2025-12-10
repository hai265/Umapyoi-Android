package com.example.uma.ui.screens.supportcard

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.uma.data.models.SupportCardListItem
import com.example.uma.ui.screens.common.ImageWithBottomText
import com.example.uma.ui.screens.common.ScreenWithSearchBar

@Composable
fun SupportCardListScreen(modifier: Modifier = Modifier, onTapSupportCard: (Int) -> Unit) {
    val viewModel: SupportCardListViewModel = hiltViewModel()
    val umaListState by viewModel.uiState.collectAsState()
    val gridState = rememberLazyGridState()

    // Scroll to top of list when list changes (from search, etc.)
    //TODO: This now causes a bug where if a user backs out from the support card detailed page the list scrolls back to the first position
    //TODO: Also refactor this and list to a separate component
    LaunchedEffect(umaListState.list) {
        gridState.scrollToItem(0)
    }

    ScreenWithSearchBar(
        textFieldState = viewModel.searchTextBoxState,
        onRefresh = { viewModel.refreshList() },
        contentEmpty = umaListState.list.isEmpty(),
        searchBoxLabel = "Search Support Card",
        syncing = umaListState.syncing
    ) {
        SupportCardGrid(umaListState.list, onTapSupportCard, gridState = gridState)
    }
}

@Composable
fun SupportCardGrid(
    cards: List<SupportCardListItem>,
    onTapCard: (Int) -> Unit,
    modifier: Modifier = Modifier,
    gridState: LazyGridState,
) {
    LazyVerticalGrid(columns = GridCells.Fixed(3), state = gridState, modifier = modifier) {
        items(items = cards, key = { it.id }) { card ->
            ImageWithBottomText(
                onClickImage = { onTapCard(card.id) },
                bottomText = card.characterName,
                imageUrl = card.imageUrl,
            )
        }
    }
}