package com.hector.pokemonapp.presentation.features.pokemonsDetails

data class PokemonDetailsScreenState(
    val pokemonDetailsViewItem: PokemonDetailsViewItem,
)

data class PokemonDetailsViewItem(
    val name: String,
    val height: Int,
    val weight: Int,
    val imageUrl: String,
    val types: String,
)
