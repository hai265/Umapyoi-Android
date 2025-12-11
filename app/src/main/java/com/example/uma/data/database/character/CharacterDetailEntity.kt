package com.example.uma.data.database.character

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.uma.data.models.CharacterBasic

@Entity(tableName = "characterDetails")
data class CharacterDetailEntity(
    @PrimaryKey
    val id: Int,

    val birthDay: Int?,
    val birthMonth: Int?,

    val category: String,
    val colorMain: String,
    val colorSub: String,

    val date: String, // raw RFC1123 timestamp string

    val dateGmt: String?,
    val modifiedGmt: String?,

    val detailImgPc: String?,
    val detailImgSp: String?,

    val earsFact: String?,
    val familyFact: String?,

    val gameId: Int?,
    val grade: String?,
    val height: Int?,

    val link: String?,
    val nameEn: String,
    val nameEnInternal: String?,
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
    val thumbImg: String?,
    val voiceUrl: String?,
    val weaknesses: String?,

    val weight: String?
)

fun CharacterDetailEntity.toCharacterBasic(): CharacterBasic {
    return CharacterBasic(
        id = id,
        gameId = gameId,
        name = nameEn,
        image = thumbImg ?: "",
        colorMain = colorMain,
        colorSub = colorSub,
    )
}


fun CharacterEntity.toCharacterBasic() = CharacterBasic(
    id = id,
    gameId = gameId,
    name = name,
    image = image,
    colorMain = colorMain,
    colorSub = colorSub
    //TODO
)
