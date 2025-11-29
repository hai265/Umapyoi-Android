package com.example.uma.ui.screens.randomgame

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.uma.ui.screens.umalist.Character
import com.example.uma.ui.theme.UmaTheme

@Composable
fun RandomUmaScreen(modifier: Modifier = Modifier) {
    val randomUmaViewModel: RandomUmaViewModel = hiltViewModel()
    val umaUiState = randomUmaViewModel.randomUmaUiState
    Column(modifier = modifier) {
        when (umaUiState) {
            is RandomUmaUiState.Error -> Text("Error: ${umaUiState.error}")
            RandomUmaUiState.Loading -> Text("Loading...")
            is RandomUmaUiState.Success -> SuccessScreen(umaUiState.character)

            RandomUmaUiState.Initial -> Text("Do you want an Uma?")
        }
        Button(
            onClick = randomUmaViewModel::getRandomUma,
            enabled = umaUiState !is RandomUmaUiState.Loading
        ) {
            Text("Click to get a random uma")
        }
    }

}

@Composable
private fun SuccessScreen(character: Character, modifier: Modifier = Modifier) {
    CharacterScreen(
        character,
        modifier = modifier
    )
}


@Composable
private fun CharacterScreen(character: Character, modifier: Modifier) {
    Column(modifier = modifier) {
        Text(character.name)
        //TODO: Imagebuilder
        AsyncImage(
            model = character.image, contentDescription = null,
            modifier
                .height(300.dp)
        )
    }
}

@Composable
@Preview
private fun CharacterScreenPreview() {
    CharacterScreen(
        Character.createWithIdNameImageOnly(
            1,
            gameId = 1,
            name = "Special Week",
            image = ""
        ), modifier = Modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    UmaTheme {
        RandomUmaScreen(modifier = Modifier)
    }
}