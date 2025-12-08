package com.example.uma.ui.screens.umalist.sorting

import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

//TODO: https://developer.android.com/develop/ui/compose/text/user-input?textfield=state-based#textfield-nav
@Composable
fun CharacterSearchTextField(textFieldState: TextFieldState, modifier: Modifier = Modifier) {
    TextField(
        state = textFieldState,
        placeholder = { Text("Special Week") },
        label = { Text("Search Character") },
        modifier = modifier,
        lineLimits = TextFieldLineLimits.SingleLine,
    )
}

@Composable
@Preview
fun CharacterSearchTextFieldPreview() {
    CharacterSearchTextField(TextFieldState())
}