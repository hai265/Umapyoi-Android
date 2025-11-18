package com.example.uma.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.uma.ui.models.UmaCharacter
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.uma.R

@Composable
fun UmaListScreen(umaCharacters: List<UmaCharacter>, modifier: Modifier = Modifier) {
    //TODO: Create viewmodel here or in the activity and pass it in?
    LazyColumn(modifier) {
        items(umaCharacters) { character ->
            //TODO: Add onclick
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier.clickable(enabled = true, onClick = {} )) {
                Text(character.name)
                AsyncImage(
                    model = character.image, contentDescription = null,
                    modifier = modifier.height(150.dp),
                    //TODO: special week icon ONLY FOR PREVIEW
                    placeholder = painterResource(R.drawable.specialweek_icon),
                )
            }
        }
    }
}

@Preview
@Composable
fun UmaListScreenPreview() {
    val umaList =
        listOf<UmaCharacter>(UmaCharacter("Special Week", ""), UmaCharacter("Tokai Teio", ""))
    UmaListScreen(umaList)
}