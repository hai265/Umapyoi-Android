package com.example.uma.data.database.character

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class CharacterEntity(
    // Initial values that get populate from umapyoi list call
    @PrimaryKey
    //Id refers to a single "entity", e.g "Special Week" character and her support cards
    // are separate entities with separate IDs, but they all share the same characterId
    val id: Int,
    val characterId: Int?,
    val nameEn: String,
    val image: String,
    val categoryLabelJp: String,
    val categoryLabelEn: String,
    val colorMain: String,
    val colorSub: String,

    // Optional Fields for character details
    val birthDay: Int?,
    val birthMonth: Int?,
    val date: String?,
    val dateGmt: String?,
    val modifiedGmt: String?,
    val detailImgPc: String?,
    val detailImgSp: String?,
    val earsFact: String?,
    val familyFact: String?,
    val grade: String?,
    val height: Int?,
    val link: String?,
    val nameJp: String?,
    val profile: String?,
    val residence: String?,
    val shoeSize: String?,
    val sizeB: Int?,
    val sizeH: Int?,
    val sizeW: Int?,
    val slogan: String?,
    val snsIcon: String?,
    val strengths: String?,
    val tailFact: String?,
    val voiceUrl: String?,
    val weaknesses: String?,
    val weight: String?,

    //User- set fields, we just have favorites for now
    val isFavorite: Boolean = false
)