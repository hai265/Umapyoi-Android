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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.uma.ui.screens.common.ImageWithBottomText
import com.example.uma.ui.screens.common.ScreenWithSearchBar
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first

@Composable
fun SupportCardListScreen(modifier: Modifier = Modifier, onTapSupportCard: (Int) -> Unit) {
    val viewModel: SupportCardListViewModel = hiltViewModel()
    val supportCardListState by viewModel.uiState.collectAsState()
    val gridState = rememberLazyGridState()

    var previousListSize by remember { mutableStateOf(supportCardListState.list.size) }
    LaunchedEffect(supportCardListState.list.size) {
        val currentSize = supportCardListState.list.size
        if (currentSize != previousListSize) {
            snapshotFlow { gridState.layoutInfo.visibleItemsInfo.isNotEmpty() }
                .filter { it }
                .first()
            gridState.scrollToItem(0)
            previousListSize = currentSize
        }
    }

    //TODO: On filter, sort characters alphabetically as well
    ScreenWithSearchBar(
        textFieldState = viewModel.searchTextBoxState,
        onRefresh = { viewModel.refreshList() },
        contentEmpty = supportCardListState.list.isEmpty(),
        searchBoxLabel = "Search Support Card",
        syncing = supportCardListState.syncing,
        modifier = modifier
    ) {
        SupportCardGrid(supportCardListState.list, onTapSupportCard, gridState = gridState)
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