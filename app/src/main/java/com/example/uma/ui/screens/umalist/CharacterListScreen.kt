package com.example.uma.ui.screens.umalist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.uma.data.models.Character
import com.example.uma.ui.screens.common.ImageWithBottomText
import com.example.uma.ui.screens.umalist.sorting.CharacterSearchTextField

@Composable
fun CharacterListScreen(modifier: Modifier = Modifier, onTapCharacter: (Int) -> Unit) {
    val viewModel: UmaListViewModel = hiltViewModel()
    val umaListState by viewModel.umaListState.collectAsState()
    val gridState = rememberLazyGridState()

    // Scroll to top of list when list changes (from search, etc.)
    LaunchedEffect(umaListState.umaList) {
        gridState.scrollToItem(0)
    }

    Column(modifier = modifier.fillMaxSize()) {
        CharacterSearchTextField(
            viewModel.searchTextBoxState,
            modifier = Modifier.fillMaxWidth()
        )
        CharacterColumn(
            characters = umaListState.umaList,
            onTapCharacter = onTapCharacter,
            state = gridState,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun CharacterColumn(
    characters: List<Character>,
    onTapCharacter: (Int) -> Unit,
    modifier: Modifier = Modifier,
    state: LazyGridState,
) {
    LazyVerticalGrid(columns = GridCells.Fixed(3), state = state, modifier = modifier) {
        items(items = characters, key = { characters: Character -> characters.id }) { character ->
            ImageWithBottomText(
                onClickImage = { onTapCharacter(character.id) },
                bottomText = character.name,
                imageUrl = character.image,
            )
        }
    }
}

@Preview
@Composable
fun CharacterColumnPreview() {
    val characterList =
        listOf<Character>(
            Character.createWithIdNameImageOnly(1, 1, "Special Week", ""),
            Character.createWithIdNameImageOnly(2, 2, "Tokai Teio", ""),
            Character.createWithIdNameImageOnly(3, 2, "Silence Suzuka", ""),
        )
    CharacterColumn(characterList, onTapCharacter = {}, state = rememberLazyGridState())
}