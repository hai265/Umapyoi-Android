package com.example.uma.ui.screens.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.uma.R

@Composable
fun ImageWithFavoriteButton(
    onClickImage: () -> Unit,
    bottomText: String,
    imageUrl: String,
    isFavorite: Boolean,
    onTapFavorite: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        ImageWithBottomText(
            onClickImage = onClickImage,
            bottomText = bottomText,
            imageUrl = imageUrl,
        )
        val carrotIcon = if (isFavorite) R.drawable.carrot_filled else R.drawable.carrot_unfilled
        Image(
            painter = painterResource(id = carrotIcon), // Replace with your icon resource
            contentDescription = "Top right icon",
            modifier = Modifier
                .align(Alignment.TopEnd) // Align to the top-right corner of the Box
                .padding(0.dp) // Add some padding for better visual appearance
                // Set the desired size for the icon
                .fillMaxWidth(0.2f)
                .clickable {
                    onTapFavorite()
                }
        )
    }
}

@Composable
@Preview
fun ImageWithFavoriteButtonIsFavoritedPreview(
) {
    ImageWithFavoriteButton(
        onClickImage = {},
        imageUrl = "",
        bottomText = "",
        isFavorite = true,
        onTapFavorite = {}
    )
}

@Composable
@Preview
fun ImageWithFavoriteButtonIsNotFavoritedPreview(
) {
    ImageWithFavoriteButton(
        onClickImage = {},
        imageUrl = "",
        bottomText = "",
        isFavorite = false,
        onTapFavorite = {}
    )
}