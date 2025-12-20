package com.example.uma.ui.screens.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt

@Composable
fun GradientBackground(
    primaryColorHex: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val primaryColor = try {
        Color(primaryColorHex.toColorInt())
    } catch (e: Exception) {
        Color.Gray
    }
        Box(
            modifier = modifier.background(
                Brush.verticalGradient(
                    colors = listOf(
                        primaryColor.copy(alpha = 0.7f),     // End with primary color
                        Color.Transparent,
                        Color.Transparent,

                        ),
                )
            )
        ) {
            content()
        }
}

@Composable
@Preview
private fun GradientBackgroundPreview(
) {
    GradientBackground(String.format("#%08X", Color.Red.toArgb()), Modifier.size(300.dp)) {
    }
}