package com.example.uma.data.database.character

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class CharacterEntity(
    // Initial values that get populate from umapyoi list call
    @PrimaryKey
    val id: Int,
    val gameId: Int?,
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
    val weight: String?
)