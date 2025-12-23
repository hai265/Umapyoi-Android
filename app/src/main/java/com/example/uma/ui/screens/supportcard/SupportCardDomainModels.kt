package com.example.uma.ui.screens.supportcard

import com.example.uma.data.models.SupportCardDetailed

data class SupportCardDetailsUiModel(
    val details: SupportCardDetailed,
    val characterName: String,
)

data class SupportCardListItem(
    val id: Int,
    val characterId: Int,
    val imageUrl: String,
    val characterName: String,
)