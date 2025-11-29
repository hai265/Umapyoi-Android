package com.example.uma.ui.screens.supportcard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.uma.R

@Composable
fun SupportCardDetailsScreen(modifier: Modifier = Modifier) {
    val viewModel: SupportCardDetailsViewModel = hiltViewModel()
    val supportCardDetailsState by viewModel.state.collectAsState()

    when (val state = supportCardDetailsState) {
        is SupportCardDetailsScreenUiState.Error -> Text("Error: $state")
        SupportCardDetailsScreenUiState.Loading -> Text("Loading...")
        is SupportCardDetailsScreenUiState.Success -> SuccessScreen(supportCard = state.supportCard)
    }
}

@Composable
fun SuccessScreen(supportCard: SupportCard, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
//        TODO: Get character name
        Text(supportCard.id.toString(), fontSize = 32.sp)
        Text("character id: ${supportCard.characterId}")
        Text(
            supportCard.titleEn,
            textAlign = TextAlign.Center
        )
        AsyncImage(
            error = painterResource(R.drawable.ic_connection_error),
            placeholder = painterResource(R.drawable.specialweek_icon),
            model = ImageRequest.Builder(LocalContext.current)
                .data(supportCard.imageUrl)
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