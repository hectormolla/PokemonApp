package com.hector.pokemonapp.domain.repository

import com.hector.pokemonapp.common.result.AppResult
import com.hector.pokemonapp.domain.entities.PaginatedPokemons
import com.hector.pokemonapp.domain.entities.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    fun getPokemonPage(page: Int): Flow<PaginatedPokemons>
    suspend fun getPokemonDetail(name: String): AppResult<Pokemon>
}
