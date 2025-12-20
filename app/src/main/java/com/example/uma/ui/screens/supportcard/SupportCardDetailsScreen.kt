package com.example.uma.ui.screens.supportcard

import android.icu.text.DateFormat
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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

    Box(modifier = modifier) {
        when (val state = supportCardDetailsState) {
            is SupportCardDetailsScreenUiState.Error -> Text("Error: $state")
            SupportCardDetailsScreenUiState.Loading -> Text("Loading...")
            is SupportCardDetailsScreenUiState.Success -> SuccessScreen(
                supportCardUiModel = state.supportCard,
                modifier = Modifier.padding(start = 20.dp, end = 20.dp)
            )
        }
    }
}

@Composable
fun SuccessScreen(supportCardUiModel: SupportCardDetailsUiModel, modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        val supportCard = supportCardUiModel.details
        Text(supportCardUiModel.characterName, fontSize = 32.sp)
        supportCard.titleEn?.let {
            Text(
                supportCard.titleEn,
                textAlign = TextAlign.Center
            )
        }
        AsyncImage(
            error = painterResource(R.drawable.ic_connection_error),
            model = ImageRequest.Builder(LocalContext.current)
                .data(supportCard.imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = modifier
                .fillMaxWidth()
        )
        supportCard.rarity?.let {
            Text(
                supportCard.rarity.name,
                textAlign = TextAlign.Center
            )
        }
        supportCard.cardType?.let {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    supportCard.cardType.name,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.size(4.dp))
                AsyncImage(
                    error = painterResource(R.drawable.ic_connection_error),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(supportCard.typeIconUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                )
            }
        }
        supportCard.dateAddedMs?.let {
            Row {
                Text(
                    "Date added: ${DateFormat.getDateInstance().format(supportCard.dateAddedMs)}",
                )
            }
        }
    }
}