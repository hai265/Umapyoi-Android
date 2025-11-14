package com.example.uma.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.uma.ui.models.UmaCharacter
import com.example.uma.ui.theme.UmaTheme

@Composable
fun HomeScreen(viewModel: UmaViewModel = viewModel(), modifier: Modifier = Modifier) {
    val umaUiState = viewModel.umaUiState
    Column(modifier = modifier) {
        when (umaUiState) {
            UmaUiState.Error -> Text("Error. Please turn the internet")
            UmaUiState.Loading -> Text("Loading...")
            is UmaUiState.Success -> SuccessScreen(umaUiState.umaCharacter)

            UmaUiState.Initial -> Text("Do you want an Uma?")
        }
        if (umaUiState !is UmaUiState.Loading) {
            Button(onClick = { viewModel.getRandomUma() }) {
                Text("Click to get a random uma")
            }
        }
    }

}

@Composable fun SuccessScreen(umaCharacter: UmaCharacter, modifier: Modifier = Modifier) {
    CharacterScreen(
        umaCharacter,
        modifier = modifier
    )
}


@Composable
fun CharacterScreen(character: UmaCharacter, modifier: Modifier) {
    Column(modifier = modifier) {
        Text(character.name)
        AsyncImage(model = character.image, contentDescription = null)
    }
}

@Composable
@Preview
fun CharacterScreenPreview() {
    CharacterScreen(UmaCharacter("Special Week", ""), modifier = Modifier)
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    UmaTheme {
        HomeScreen(modifier = Modifier)
    }
}