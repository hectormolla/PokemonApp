package com.hector.pokemonapp.presentation.features.pokemonsDetails

sealed class PokemonDetailsScreenState {
    data class Success(
        val pokemonDetailsViewItem: PokemonDetailsViewItem,
    ): PokemonDetailsScreenState()

    data class Error(
        val messageResId: Int,
    ): PokemonDetailsScreenState()

}

data class PokemonDetailsViewItem(
    val name: String,
    val height: Int,
    val weight: Int,
    val imageUrl: String,
    val types: String,
)
