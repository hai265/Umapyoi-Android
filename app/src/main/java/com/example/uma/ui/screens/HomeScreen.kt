package com.example.uma.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.uma.R
import com.example.uma.ui.models.UmaCharacter
import com.example.uma.ui.theme.UmaTheme

@Composable
fun HomeScreen(viewModel: UmaViewModel = UmaViewModel(), modifier: Modifier = Modifier) {
    when(viewModel.umaUiState) {
        UmaUiState.Error -> TODO()
        UmaUiState.Loading -> TODO()
        is UmaUiState.Success -> CharacterScreen(
            (viewModel.umaUiState as UmaUiState.Success).umaCharacter,
            modifier = modifier
        )
    }

}


@Composable
fun CharacterScreen(character: UmaCharacter, modifier: Modifier) {
    Column(modifier = modifier) {
        Text(character.name)
        Image(painter = painterResource(character.characterImage), contentDescription = null)
    }
}

@Composable
@Preview
fun CharacterScreenPreview() {
    CharacterScreen(UmaCharacter("Special Week", R.drawable.specialweek_icon), modifier = Modifier)
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    UmaTheme {
        HomeScreen(modifier = Modifier)
    }
}