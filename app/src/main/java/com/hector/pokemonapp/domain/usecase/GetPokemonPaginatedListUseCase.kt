package com.hector.pokemonapp.domain.usecase

import com.hector.pokemonapp.domain.entities.PaginatedPokemons
import kotlinx.coroutines.flow.Flow

interface GetPokemonPaginatedListUseCase {
    operator fun invoke(page: Int): Flow<PaginatedPokemons>
}
