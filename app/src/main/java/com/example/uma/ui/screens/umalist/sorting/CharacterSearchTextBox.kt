package com.example.uma.ui.screens.umalist.sorting

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

//TODO: https://developer.android.com/develop/ui/compose/text/user-input?textfield=state-based#textfield-nav
@Composable
fun SearchTextField(
    textFieldState: TextFieldState,
    searchBoxLabel: String,
    modifier: Modifier = Modifier
) {
    TextField(
        state = textFieldState,
        placeholder = { Text("Special Week") },
        label = { Text(searchBoxLabel) },
        modifier = modifier,
        lineLimits = TextFieldLineLimits.SingleLine,
        trailingIcon = {
            if (textFieldState.text.isNotEmpty()) {
                Image(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        textFieldState.clearText()
                    },
                )
            }
        }
    )
}

@Composable
@Preview
fun SearchTextFieldPreview() {
    SearchTextField(TextFieldState(), "")
}