package com.example.uma.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey
    val id: Int = 0,
    val name: String,
    val image: String,
    val categoryLabelJp: String,
    val categoryLabelEn: String,
    val colorMain: String,
    val colorSub: String,
)