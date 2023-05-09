package com.hector.pokemonapp.data.api.model

import kotlinx.serialization.Serializable

@Serializable
data class PokemonPageResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonResponse>,
)
