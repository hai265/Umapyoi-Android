package com.example.uma.ui.models

import androidx.annotation.DrawableRes
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//TODO: Change to serializable?
@Serializable
data class UmaCharacter(
    @SerialName("name_en")
    val name: String,
)
