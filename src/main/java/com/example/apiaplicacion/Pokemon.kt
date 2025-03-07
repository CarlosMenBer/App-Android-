package com.example.apiaplicacion
//API
data class Pokemon(
    val name: String,
    val id: Int,
    val height: Int,
    val weight: Int,
    val types: List<Type>,
    val sprites: Sprites
)

data class Type(
    val type: TypeName
)

data class TypeName(
    val name: String
)

data class Sprites(
    val front_default: String
)
