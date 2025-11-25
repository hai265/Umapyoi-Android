package com.example.uma.ui.screens.umalist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.uma.R
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
                SuccessScreen(
                    id = id,
                    character = state.detailedCharacterInfo,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

}

@Composable
private fun SuccessScreen(
    id: Int,
    character: DetailedCharacterInfo,
    modifier: Modifier = Modifier
) {
    Profile(
        id = id,
        character = character,
        modifier = modifier
    )
}


@Composable
//Id just for testing
private fun Profile(id: Int? = 0, character: DetailedCharacterInfo, modifier: Modifier) {
    Column(modifier = modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(character.name, fontSize = 32.sp)
        Text("id: $id")
        Text(character.slogan, textAlign = TextAlign.Center)
        AsyncImage(
            error = painterResource(R.drawable.ic_connection_error),
            placeholder = painterResource(R.drawable.specialweek_icon),
            model = ImageRequest.Builder(LocalContext.current)
                .data(character.thumbImg)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = modifier
                .height(300.dp)
        )
//        TODO: Add Japanese name, voice actor, birthday, school, dorm
//        TODO: Add slogan with profile picture
//        TODO: Add measurements
//        TODO: Add profile (strong / weak points, ears, tail, family
//        TODO: Add secret facts
//        TODO: Add Character Versions/ Outfits
//        TODO: Add support cards
    }
}

@Composable
@Preview
private fun ProfilePreview() {
    Profile(
        0,
        DetailedCharacterInfo(
            1,
            name = "Special Week",
            birthMonth = 0,
            thumbImg = "",
            slogan = "My name is Special Week! My dream is to be the best Umamusume in Japan! I'm gonna pull my own weight to make my moms proud!",
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