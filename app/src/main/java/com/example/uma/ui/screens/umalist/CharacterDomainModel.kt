package com.example.uma.ui.screens.umalist

//TODO: Move to data module
data class Character(
    val id: Int,
    val gameId: Int?,
    val name: String,
    val image: String,

    val birthDate: BirthDate?,
    val profile: CharacterProfile?,
//    val physicalTraits: PhysicalTraits?,
//    val metadata: CharacterMetadata?,
) {
    companion object {
        fun createWithIdNameImageOnly(id: Int, gameId: Int?, name: String, image: String): Character {
            return Character(
                id, gameId, name, image,
                birthDate = null,
                profile = null,
            )
        }
    }
}

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