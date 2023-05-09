package com.hector.pokemonapp.domain.usecase.impl

import com.hector.pokemonapp.domain.entities.PaginatedPokemons
import com.hector.pokemonapp.domain.repository.PokemonRepository
import com.hector.pokemonapp.domain.usecase.GetPokemonPaginatedListUseCase
import kotlinx.coroutines.flow.Flow

internal class GetPokemonPaginatedListUseCaseImpl(
    private val repository: PokemonRepository,
) : GetPokemonPaginatedListUseCase {
    override fun invoke(page: Int): Flow<PaginatedPokemons> =
        repository.getPokemonPage(page = page)
}
