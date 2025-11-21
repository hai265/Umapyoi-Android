package com.example.uma.ui.screens.umalist

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CharacterScreen(id: Int, modifier: Modifier = Modifier) {
    Text("Character: $id")
}