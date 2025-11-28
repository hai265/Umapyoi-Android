package com.example.uma.ui.screens.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.uma.R

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
            .height(200.dp)
            .padding(4.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier.height(150.dp),
            //TODO: special week icon ONLY FOR PREVIEW
            placeholder = painterResource(R.drawable.specialweek_icon),
        )
        Text(bottomText, textAlign = TextAlign.Center)
    }
}