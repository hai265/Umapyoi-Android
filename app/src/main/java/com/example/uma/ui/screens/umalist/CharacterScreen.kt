package com.example.uma.ui.screens.umalist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.uma.ui.models.UmaCharacter
import com.example.uma.ui.theme.UmaTheme
import androidx.compose.runtime.getValue

@Composable
fun CharacterScreen(id: Int, modifier: Modifier = Modifier) {
    val characterScreenViewModel: CharacterScreenViewModel =
        hiltViewModel<CharacterScreenViewModel, CharacterScreenViewModel.Factory>(
            creationCallback = {factory -> factory.create(id)}
        )

    val umaUiState by characterScreenViewModel.state.collectAsState()
    Column(modifier = modifier) {
        val state = umaUiState
        when (state) {
            is CharacterScreenUiState.Error -> Text("Error: $state")
            CharacterScreenUiState.Loading -> Text("Loading...")
            is CharacterScreenUiState.Success -> {SuccessScreen(state.umaCharacter)}
        }
    }

}

@Composable
private fun SuccessScreen(umaCharacter: UmaCharacter, modifier: Modifier = Modifier) {
    CharacterScreen(
        umaCharacter,
        modifier = modifier
    )
}


@Composable
private fun CharacterScreen(character: UmaCharacter, modifier: Modifier) {
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
    CharacterScreen(UmaCharacter(1, "Special Week", ""), modifier = Modifier)
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    UmaTheme {
        CharacterScreen(1, modifier = Modifier)
    }
}