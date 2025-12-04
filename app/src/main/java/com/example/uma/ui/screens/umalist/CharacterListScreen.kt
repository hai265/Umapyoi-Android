package com.example.uma.ui.screens.umalist

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.uma.ui.screens.common.ImageWithBottomText

@Composable
fun CharacterListScreen(modifier: Modifier = Modifier, onTapCharacter: (Int) -> Unit) {
    val viewModel: UmaListViewModel = hiltViewModel()
    val umaListState by viewModel.umaList.collectAsState()

    CharacterColumn(
        characters = umaListState.umaList,
        onTapCharacter = onTapCharacter,
        modifier = modifier
    )
}

@Composable
fun CharacterColumn(
    characters: List<Character>,
    onTapCharacter: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(columns = GridCells.Fixed(3), modifier) {
        items(items = characters, key = { it.id }) { character ->
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
            Character.createWithIdNameImageOnly(2,2, "Tokai Teio", ""),
            Character.createWithIdNameImageOnly(3,2, "Silence Suzuka", ""),
        )
    CharacterColumn(characterList, onTapCharacter = {})
}