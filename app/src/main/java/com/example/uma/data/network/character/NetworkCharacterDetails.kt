package com.example.uma.data.network.character
import com.example.uma.data.database.character.CharacterDetailEntity
import com.example.uma.data.database.character.CharacterEntity
import com.example.uma.ui.screens.umalist.BirthDate
import com.example.uma.ui.screens.umalist.Character
import com.example.uma.ui.screens.umalist.CharacterProfile
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkCharacterDetails(
    @SerialName("birth_day") val birthDay: Int?,
    @SerialName("birth_month") val birthMonth: Int?,
    @SerialName("category_label") val categoryLabel: String,
    @SerialName("category_label_en") val categoryLabelEn: String,
    @SerialName("category_value") val categoryValue: String,
    @SerialName("color_main") val colorMain: String,
    @SerialName("color_sub") val colorSub: String,
    @SerialName("date_gmt") val dateGmt: String,
    @SerialName("detail_img_pc") val detailImgPc: String?,
    @SerialName("detail_img_sp") val detailImgSp: String?,
    @SerialName("ears_fact") val earsFact: String?,
    @SerialName("family_fact") val familyFact: String?,
    @SerialName("game_id") val gameId: Int?,
    @SerialName("grade") val grade: String?,
    @SerialName("height") val height: Int?,
    @SerialName("id") val id: Int,
    @SerialName("link") val link: String,
    @SerialName("modified_gmt") val modifiedGmt: String,
    @SerialName("name_en") val nameEn: String,
    @SerialName("name_en_internal") val nameEnInternal: String,
    @SerialName("name_jp") val nameJp: String,
    @SerialName("preferred_url") val preferredUrl: String,
    @SerialName("profile") val profile: String?,
    @SerialName("residence") val residence: String?,
    @SerialName("row_number") val rowNumber: Int,
    @SerialName("shoe_size") val shoeSize: String?,
    @SerialName("size_b") val sizeB: Int?,
    @SerialName("size_h") val sizeH: Int?,
    @SerialName("size_w") val sizeW: Int?,
    @SerialName("slogan") val slogan: String?,
    @SerialName("sns_header") val snsHeader: String,
    @SerialName("sns_icon") val snsIcon: String,
    @SerialName("strengths") val strengths: String?,
    @SerialName("tail_fact") val tailFact: String?,
    @SerialName("thumb_img") val thumbImg: String,
    @SerialName("voice") val voice: String?,
    @SerialName("weaknesses") val weaknesses: String?,
    @SerialName("weight") val weight: String?,
)

fun NetworkCharacterDetails.toDetailedCharacterEntity(): CharacterDetailEntity =
    CharacterDetailEntity(
        id = id,
        nameEn = nameEn,
        thumbImg = thumbImg,
        category = categoryLabelEn,
        colorMain = colorMain,
        colorSub = colorSub,
        date = dateGmt,
        slogan = slogan,
        birthDay = null,
        birthMonth = null,
        dateGmt = null,
        modifiedGmt = null,
        detailImgPc = null,
        detailImgSp = null,
        earsFact = null,
        familyFact = null,
        gameId = null,
        grade = null,
        height = null,
        link = null,
        nameEnInternal = null,
        nameJp = null,
        profile = null,
        residence = null,
        shoeSize = null,
        sizeB = null,
        sizeH = null,
        sizeW = null,
        snsIcon = null,
        strengths = null,
        tailFact = null,
        voice = null,
        weaknesses = null,
        weight = null,
    )

fun NetworkListCharacter.toCharacterEntity() = CharacterEntity(
    id = id,
    name = name,
    image = image,
    categoryLabelJp = categoryLabelJp,
    categoryLabelEn = categoryLabelEn,
    colorMain = colorMain,
    colorSub = colorSub,
)

fun NetworkCharacterDetails.toCharacter(): Character {
    return Character(
        id = id,
        name = nameEn,
        image = thumbImg,
        birthDate = BirthDate.createBirthDate(birthDay, birthMonth),
        profile = CharacterProfile(
            slogan = slogan,
            category = categoryLabelEn
        ),
    )
}
