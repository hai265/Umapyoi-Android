package com.example.uma.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.uma.ui.models.UmaCharacter
import com.example.uma.ui.theme.UmaTheme

@Composable
fun HomeScreen(umaUiState: UmaUiState, buttonOnclick: () -> Unit, modifier: Modifier = Modifier) {
    val umaUiState = umaUiState
    Column(modifier = modifier) {
        when (umaUiState) {
            is UmaUiState.Error -> Text("Error: ${umaUiState.error}")
            UmaUiState.Loading -> Text("Loading...")
            is UmaUiState.Success -> SuccessScreen(umaUiState.umaCharacter)

            UmaUiState.Initial -> Text("Do you want an Uma?")
        }
        Button(onClick = buttonOnclick, enabled = umaUiState !is UmaUiState.Loading) {
            Text("Click to get a random uma")
        }
    }

}

@Composable
fun SuccessScreen(umaCharacter: UmaCharacter, modifier: Modifier = Modifier) {
    CharacterScreen(
        umaCharacter,
        modifier = modifier
    )
}


@Composable
fun CharacterScreen(character: UmaCharacter, modifier: Modifier) {
    Column(modifier = modifier) {
        Text(character.name)
        AsyncImage(
            model = character.image, contentDescription = null,
            modifier
                .height(300.dp)
        )
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
        HomeScreen(UmaUiState.Error("error"), {}, modifier = Modifier)
    }
}