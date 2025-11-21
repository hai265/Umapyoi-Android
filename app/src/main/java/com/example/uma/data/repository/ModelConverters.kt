package com.example.uma.data.repository

import com.example.uma.data.network.NetworkUmaCharacter

fun NetworkUmaCharacter.toUmaCharacter() = UmaCharacter(
    id = id,
    name = name,
    image = image
)