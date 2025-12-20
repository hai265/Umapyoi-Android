package com.example.uma.ui.screens.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.uma.R

//TODO: Should grid set padding or this
@Composable
fun ImageWithBottomText(
    onClickImage: () -> Unit,
    bottomText: String,
    imageUrl: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(enabled = true, onClick = onClickImage)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            //TODO: special week icon ONLY FOR PREVIEW
            placeholder = painterResource(R.drawable.specialweek_icon),
            //TODO: Only for preview delete when done
            error = painterResource(R.drawable.specialweek_icon),
            modifier = Modifier
                .fillMaxWidth()

        )

        if (bottomText.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .height(48.dp), // A fixed height that can hold 2 lines + padding
                contentAlignment = Alignment.Center // Center the content vertically and horizontally
            ) {
                Text(
                    text = bottomText,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(vertical = 4.dp),
                    maxLines = 2,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
@Preview
fun ImageWithBottomTextPreview(
) {
    ImageWithBottomText(
        onClickImage = {},
        bottomText = "Special Weekfwowefofwjeiewfoijweiofwej",
        imageUrl = "",
    )
}