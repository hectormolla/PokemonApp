package com.hector.pokemonapp.data.mappers

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import com.hector.pokemonapp.data.api.model.PokemonDetailsResponse
import com.hector.pokemonapp.data.api.model.PokemonPageResponse
import com.hector.pokemonapp.domain.entities.PaginatedPokemons
import com.hector.pokemonapp.domain.entities.Pokemon

internal fun PokemonDetailsResponse.toPokemon() = Pokemon(
    id = id,
    name = name,
    height = height,
    weight = weight,
    imageUrl = sprites.other.dreamWorld.front,
    types = types.map { it.type.name },
)

internal fun PokemonPageResponse.toPaginatedPokemons(
    page: Int,
    pokemons: List<Pokemon>,
) = PaginatedPokemons(
    count = count,
    page = page,
    pageSize = PAGE_SIZE,
    pokemons = pokemons,
)
