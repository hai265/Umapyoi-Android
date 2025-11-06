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
fun HomeScreen(modifier: Modifier) {
    Text(
        text = "Hello",
        modifier = modifier
    )
}


@Composable
fun CharacterScreen(character: UmaCharacter, modifier: Modifier) {
    Column {
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