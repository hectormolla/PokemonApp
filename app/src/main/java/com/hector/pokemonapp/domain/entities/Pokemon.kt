package com.hector.pokemonapp.domain.entities

data class Pokemon(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val imageUrl: String,
    val types: List<String>,
)
