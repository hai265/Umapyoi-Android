package com.example.uma.ui.screens.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.uma.R

@Composable
fun ImageWithFavoriteButton(
    onClickImage: () -> Unit,
    imageUrl: String,
    primaryColorHex: String,
) {
    val primaryColor = try {
        Color(primaryColorHex.toColorInt())
    } catch (e: Exception) {
        Color.Gray
    }
    Card {
        Box(
            modifier = Modifier
                .clickable(enabled = true, onClick = onClickImage)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            primaryColor.copy(alpha = 0.7f),     // End with primary color
                            Color.Transparent,
                            Color.Transparent,
                        ),
                    )
                )
                .fillMaxWidth()
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
                modifier = Modifier.fillMaxWidth()
            )

            Image(
                painter = painterResource(id = R.drawable.carrot_filled), // Replace with your icon resource
                contentDescription = "Top right icon",
                modifier = Modifier
                    .align(Alignment.TopEnd) // Align to the top-right corner of the Box
                    .padding(0.dp) // Add some padding for better visual appearance
                    .size(48.dp) // Set the desired size for the icon
            )
        }
    }
}

@Composable
fun CardWithFavoriteButton(
    onClickImage: () -> Unit,
    bottomText: String,
    imageUrl: String,
    primaryColorHex: String,
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
            primaryColorHex = primaryColorHex,
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
fun ImageWithFavoriteButtonPreview(
) {
    ImageWithFavoriteButton(
        onClickImage = {},
        imageUrl = "",
        primaryColorHex = "#EE6DCB",
    )
}

@Composable
@Preview
fun CardWithFavoriteButtonIsFavoritedPreview(
) {
    CardWithFavoriteButton(
        onClickImage = {},
        imageUrl = "",
        primaryColorHex = "#EE6DCB",
        bottomText = "",
        isFavorite = true,
        onTapFavorite = {}
    )
}

@Composable
@Preview
fun CardWithFavoriteButtonIsNotFavoritedPreview(
) {
    CardWithFavoriteButton(
        onClickImage = {},
        imageUrl = "",
        primaryColorHex = "#EE6DCB",
        bottomText = "",
        isFavorite = false,
        onTapFavorite = {}
    )
}