package com.hector.pokemonapp.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonDetailsResponse(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val sprites: SpritesResponse,
    val types: List<TypesResponse>,
)

@Serializable
data class SpritesResponse(
    val other: OtherSpritesResponse,
)

@Serializable
data class OtherSpritesResponse(
    @SerialName("dream_world") val dreamWorld: DreamWorldResponse,
)

@Serializable
data class DreamWorldResponse(
    @SerialName("front_default") val front: String,
)

@Serializable
data class TypesResponse(
    val type: TypeResponse,
)

@Serializable
data class TypeResponse(
    val name: String,
)
