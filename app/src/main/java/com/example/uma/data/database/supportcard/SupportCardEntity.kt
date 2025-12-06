package com.example.uma.data.database.supportcard

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.uma.data.models.CardRarity
import com.example.uma.data.models.CardType

@Entity(tableName = "support_cards")
data class SupportCardEntity(
    @PrimaryKey val id: Int,
    val characterId: Int,
    val gametoraPath: String,

    // Detailed fields are nullable
    val rarity: CardRarity?,
    val dateAddedMs: Long?,
    val titleEn: String?,
    val titleJp: String?,
    val cardType: CardType?,
    val typeIconUrl: String?
)
