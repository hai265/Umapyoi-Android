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
import com.example.uma.ui.theme.UmaTheme
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.uma.ui.screens.models.DetailedCharacterInfo

@Composable
fun Profile(id: Int, modifier: Modifier = Modifier) {
    val characterScreenViewModel: CharacterScreenViewModel =
        hiltViewModel<CharacterScreenViewModel, CharacterScreenViewModel.Factory>(
            creationCallback = { factory -> factory.create(id) }
        )

    val umaUiState by characterScreenViewModel.state.collectAsState()
    Column(modifier = modifier) {
        val state = umaUiState
        when (state) {
            is CharacterScreenUiState.Error -> Text("Error: $state")
            CharacterScreenUiState.Loading -> Text("Loading...")
            is CharacterScreenUiState.Success -> {
                SuccessScreen(state.detailedCharacterInfo)
            }
        }
    }

}

@Composable
private fun SuccessScreen(character: DetailedCharacterInfo, modifier: Modifier = Modifier) {
    Profile(
        character,
        modifier = modifier
    )
}


@Composable
private fun Profile(character: DetailedCharacterInfo, modifier: Modifier) {
    Column(modifier = modifier) {
        Text(character.name)
        //TODO: Imagebuilder
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(character.thumbImg)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier
                .height(300.dp)
        )
    }
}

@Composable
@Preview
private fun ProfilePreview() {
    Profile(
        DetailedCharacterInfo(
            1,
            name = "Special Week",
            birthMonth = 0,
            thumbImg = "",
            category = ""
        ), modifier = Modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    UmaTheme {
        Profile(1, modifier = Modifier)
    }
}