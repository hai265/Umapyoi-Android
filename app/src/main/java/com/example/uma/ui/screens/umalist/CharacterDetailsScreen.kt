package com.example.uma.ui.screens.umalist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.uma.R
import com.example.uma.data.models.CharacterBasic
import com.example.uma.data.models.SupportCardBasic
import com.example.uma.ui.screens.common.ImageWithBottomText
import com.example.uma.ui.theme.UmaTheme

@Composable
fun CharacterDetailsScreen(
    id: Int,
    onTapSupportCard: (supportCardId: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val characterDetailsScreenViewModel: CharacterDetailsScreenViewModel = hiltViewModel()

    val umaUiState by characterDetailsScreenViewModel.state.collectAsState()
    Column(modifier = modifier) {
        when (val state = umaUiState) {
            is CharacterScreenUiState.Error -> Text("Error: $state")
            CharacterScreenUiState.Loading -> Text("Loading...")
            is CharacterScreenUiState.Success -> {
                SuccessScreen(
                    id = id,
                    characterBasic = state.characterBasic,
                    supportCards = state.supportCards,
                    onTapSupportCard = onTapSupportCard,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

}

@Composable
private fun SuccessScreen(
    id: Int,
    characterBasic: CharacterBasic,
    supportCards: List<SupportCardBasic>,
    modifier: Modifier = Modifier,
    onTapSupportCard: (Int) -> Unit
) {
    CharacterDetailsScreen(
        id = id,
        characterBasic = characterBasic,
        modifier = modifier,
        supportCards = supportCards,
        onTapSupportCard = onTapSupportCard
    )
}


@Composable
//Id just for testing
private fun CharacterDetailsScreen(
    id: Int = 0,
    //TODO: Replace with characterDetailed
    characterBasic: CharacterBasic,
    supportCards: List<SupportCardBasic>,
    onTapSupportCard: (supportCardId: Int) -> Unit,
    modifier: Modifier
) {
    Column(modifier = modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(characterBasic.name, fontSize = 32.sp)
        Text("id: $id")
        //TODO: Replace with characterDetailed
//        characterBasic.profile?.slogan?.let {
//            Text(
//                characterBasic.profile.slogan,
//                textAlign = TextAlign.Center
//            )
//        }
        //TODO: When add outfits, let user pick outfit, and this image changes to that outfit
        AsyncImage(
            error = painterResource(R.drawable.ic_connection_error),
            placeholder = painterResource(R.drawable.specialweek_icon),
            model = ImageRequest.Builder(LocalContext.current)
                .data(characterBasic.image)
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

        if (supportCards.isNotEmpty()) {
            Text("Support Cards", fontSize = 32.sp)
            LazyRow(modifier = Modifier.fillMaxSize()) {
                items(supportCards) { supportCard ->
                    ImageWithBottomText(
                        onClickImage = {onTapSupportCard(supportCard.id)}, //TODO: navigate to support card detail
                        bottomText = "", //TODO: make bottomText nullable for no text
                        imageUrl = supportCard.imageUrl,
                        primaryColorHex = "",
                        secondaryColorHex = "",
                    )
                }
            }
        }
    }
}

@Composable
@Preview
private fun CharacterDetailsScreenPreview() {
    CharacterDetailsScreen(
        id = 0,
        onTapSupportCard = {},
        modifier = Modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    UmaTheme {
        CharacterDetailsScreen(1, onTapSupportCard = {}, modifier = Modifier)
    }
}