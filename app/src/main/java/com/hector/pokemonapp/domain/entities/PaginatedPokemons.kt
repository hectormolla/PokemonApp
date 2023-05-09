package com.hector.pokemonapp.domain.entities

data class PaginatedPokemons(
    val count: Int,
    val page: Int,
    val pageSize: Int,
    val pokemons: List<Pokemon>,
)
