package com.example.uma.ui.models

import androidx.annotation.DrawableRes

data class UmaCharacter(
    val name: String,
    @DrawableRes
    val characterImage: Int
)
