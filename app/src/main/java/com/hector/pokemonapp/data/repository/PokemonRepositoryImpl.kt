package com.hector.pokemonapp.data.repository

import com.hector.pokemonapp.data.api.PokemonApi
import com.hector.pokemonapp.data.api.model.PokemonPageResponse
import com.hector.pokemonapp.data.base.BaseRepository
import com.hector.pokemonapp.data.mappers.toPaginatedPokemons
import com.hector.pokemonapp.data.mappers.toPokemon
import com.hector.pokemonapp.domain.entities.PaginatedPokemons
import com.hector.pokemonapp.domain.entities.Pokemon
import com.hector.pokemonapp.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow

class PokemonRepositoryImpl(
    private val api: PokemonApi,
) : PokemonRepository, BaseRepository() {

    override fun getPokemonPage(page: Int): Flow<PaginatedPokemons> = repositoryFlow {
        val pageResponse: PokemonPageResponse = api.requestPokemonPage(page = page)
        val pokemons: List<Pokemon> = pageResponse.results.map {
            api.requestPokemonDetails(name = it.name).toPokemon()
        }
        emit(
            pageResponse.toPaginatedPokemons(
                page = page,
                pokemons = pokemons,
            ),
        )
    }

    override suspend fun getPokemonDetail(name: String): Pokemon =
        api.requestPokemonDetails(name = name).toPokemon()
}
