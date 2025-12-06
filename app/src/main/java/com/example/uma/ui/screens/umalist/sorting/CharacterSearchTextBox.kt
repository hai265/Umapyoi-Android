package com.example.uma.ui.screens.umalist.sorting

import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

//TODO: https://developer.android.com/develop/ui/compose/text/user-input?textfield=state-based#textfield-nav
@Composable
fun CharacterSearchTextField() {
    TextField(
        state = rememberTextFieldState(),
        placeholder = { Text("Special Week") },
        label = { Text("Search") }
    )
}

@Composable
@Preview
fun CharacterSearchTextFieldPreview() {
    CharacterSearchTextField()
}