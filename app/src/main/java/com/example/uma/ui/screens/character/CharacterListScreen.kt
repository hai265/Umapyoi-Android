package com.example.uma.ui.screens.character

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
import com.example.uma.ui.screens.common.CardWithFavoriteButton
import com.example.uma.ui.screens.common.ScreenWithSearchBar

//TODO: Pressing back after tapping character details go back to top of list instead of previous position
//TODO: Favoriting a character resets the list to the top
@Composable
fun CharacterListScreen(modifier: Modifier = Modifier, onTapCharacter: (Int) -> Unit) {
    val viewModel: CharacterListViewModel = hiltViewModel()
    val characterListState by viewModel.uiState.collectAsState()
    val gridState = rememberLazyGridState()

    // Scroll to top of list when list changes (from search, etc.)
    LaunchedEffect(characterListState.list) {
        gridState.scrollToItem(0)
    }

    ScreenWithSearchBar(
        textFieldState = viewModel.searchTextBoxState,
        onRefresh = { viewModel.refreshList() },
        contentEmpty = characterListState.list.isEmpty(),
        searchBoxLabel = "Search Character",
        syncing = characterListState.syncing,
    ) {
        CharacterColumn(
            characterBasics = characterListState.list,
            onTapCharacter = onTapCharacter,
            state = gridState,
            onTapFavorite = { id: Int, isFavorite: Boolean ->
                viewModel.onFavoriteCharacter(
                    id,
                    isFavorite
                )
            }
        )
    }
}

@Composable
fun CharacterColumn(
    characterBasics: List<CharacterBasic>,
    onTapCharacter: (Int) -> Unit,
    modifier: Modifier = Modifier,
    state: LazyGridState,
    onTapFavorite: (Int, Boolean) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        state = state,
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = characterBasics,
            key = { characters: CharacterBasic -> characters.id }) { character ->
            CardWithFavoriteButton(
                onClickImage = { onTapCharacter(character.id) },
                bottomText = character.name,
                imageUrl = character.image,
                primaryColorHex = character.colorMain,
                isFavorite = character.isFavorite,
                onTapFavorite = { onTapFavorite(character.id, character.isFavorite.not()) }
            )
        }
    }
}

@Preview
@Composable
fun CharacterColumnPreview() {
    val characterBasicLists =
        listOf(
            CharacterBasic(1, 1, "Special Week", "", "", "", false),
            CharacterBasic(2, 2, "Tokai Teio", "", "", "", false),
            CharacterBasic(3, 2, "Silence Suzuka", "", "", "", false),
        )
    CharacterColumn(
        characterBasicLists,
        onTapCharacter = {},
        state = rememberLazyGridState(),
        onTapFavorite = { _, _ -> })
}