package com.hector.pokemonapp.domain.usecase.impl

import com.hector.pokemonapp.domain.entities.Pokemon
import com.hector.pokemonapp.domain.repository.PokemonRepository
import com.hector.pokemonapp.domain.usecase.GetPokemonDetailUseCase

class GetPokemonDetailUseCaseImpl(
    private val repository: PokemonRepository,
) : GetPokemonDetailUseCase {
    override suspend fun invoke(name: String): Pokemon = repository.getPokemonDetail(name = name)
}
