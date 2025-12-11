package com.example.uma.ui.screens.umalist

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.uma.data.models.CharacterBasic
import com.example.uma.ui.screens.common.ImageWithBottomText
import com.example.uma.ui.screens.common.ScreenWithSearchBar

//TODO: Pressing back after tapping character details go back to top of list instead of previous position
@Composable
fun CharacterListScreen(modifier: Modifier = Modifier, onTapCharacter: (Int) -> Unit) {
    val viewModel: CharacterListViewModel = hiltViewModel()
    val umaListState by viewModel.uiState.collectAsState()
    val gridState = rememberLazyGridState()

    // Scroll to top of list when list changes (from search, etc.)
    LaunchedEffect(umaListState.list) {
        gridState.scrollToItem(0)
    }

    ScreenWithSearchBar(
        textFieldState = viewModel.searchTextBoxState,
        onRefresh = { viewModel.refreshList() },
        contentEmpty = umaListState.list.isEmpty(),
        searchBoxLabel = "Search Character",
        syncing = umaListState.syncing,
    ) {
        CharacterColumn(
            characterBasics = umaListState.list,
            onTapCharacter = onTapCharacter,
            state = gridState,
        )
    }
}

@Composable
fun CharacterColumn(
    characterBasics: List<CharacterBasic>,
    onTapCharacter: (Int) -> Unit,
    modifier: Modifier = Modifier,
    state: LazyGridState,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        state = state,
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = characterBasics, key = { characters: CharacterBasic -> characters.id }) { character ->
            ImageWithBottomText(
                onClickImage = { onTapCharacter(character.id) },
                bottomText = character.name,
                imageUrl = character.image,
                primaryColorHex = character.colorMain,
                secondaryColorHex = character.colorSub,
            )
        }
    }
}

@Preview
@Composable
fun CharacterColumnPreview() {
    val characterBasicLists =
        listOf<CharacterBasic>(
            CharacterBasic(1, 1, "Special Week", "", "", ""),
            CharacterBasic(2, 2, "Tokai Teio", "", "", ""),
            CharacterBasic(3, 2, "Silence Suzuka", "", "", ""),
        )
    CharacterColumn(characterBasicLists, onTapCharacter = {}, state = rememberLazyGridState())
}