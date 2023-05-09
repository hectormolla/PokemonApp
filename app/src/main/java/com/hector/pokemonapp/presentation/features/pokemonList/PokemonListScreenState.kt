package com.hector.pokemonapp.presentation.features.pokemonList

sealed class PokemonListScreenState {
    data class Success(
        val totalCount: Int,
        val page: Int,
        val pageSize: Int,
        val pokemons: List<PokemonViewItem>,
    ) : PokemonListScreenState()

    object Loading : PokemonListScreenState()

    object Error : PokemonListScreenState()
}

data class PokemonViewItem(
    val nameId: String,
    val name: String,
    val imageUrl: String,
)
