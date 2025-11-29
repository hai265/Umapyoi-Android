package com.example.uma.ui.screens.supportcard

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.uma.ui.screens.common.ImageWithBottomText

@Composable
fun SupportCardListScreen(modifier: Modifier = Modifier, onTapSupportCard: (Int) -> Unit) {
    val viewModel: SupportCardListViewModel = hiltViewModel()
    val umaListState by viewModel.supportCardList.collectAsState()

    SupportCardGrid(umaListState.suportCardList, {})
}

@Composable
fun SupportCardGrid(
    cards: List<SupportCardListItem>,
    onTapCard: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(columns = GridCells.Fixed(3), modifier) {
        items(items = cards, key = { it.id }) { card ->
            ImageWithBottomText(
                //TODO: When click on support card open details page
                onClickImage = {onTapCard(card.id)},
                bottomText = card.characterName,
                imageUrl = card.imageUrl,
            )
        }
    }
}