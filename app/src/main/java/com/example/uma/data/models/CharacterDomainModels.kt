package com.example.uma.data.models

//TODO: Split between CharacterBasic and
data class CharacterBasic(
    val id: Int,
    val gameId: Int?,
    val name: String,
    val image: String,
    val colorMain: String,
    val colorSub: String,
)

data class CharacterDetailed(
    val characterBasic: CharacterBasic,
    val characterProfile: CharacterProfile
)

data class BirthDate(
    val day: Int,
    val month: Int
) {
    companion object {
        fun createBirthDate(day: Int?, month: Int?): BirthDate? {
            return if (day != null && month != null) {
                BirthDate(day, month)
            } else null
        }
    }
}

//data class PhysicalTraits(
//    val height: Int?,
//    val weight: String?,
//    val shoeSize: String?,
//    val familyFact: String?,
//    val tailFact: String?,
//    val earsFact: String?,
//    val sizeB: Int?,
//    val sizeW: Int?,
//    val sizeH: Int?,
//)

data class CharacterProfile(
    val slogan: String?,
    val category: String,
//    val profile: String?,
//    val voice: String?,
//    val residence: String?,
//    val grade: String?,
//    val strengths: String?,
//    val weaknesses: String?,
)

//data class CharacterMetadata(
//    val colorMain: String,
//    val colorSub: String,
//    val nameJp: String,
//    val nameInternal: String,
//    val gameId: Int?,
//    val link: String?,
//)