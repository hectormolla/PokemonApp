package com.hector.pokemonapp.domain.usecase

import com.hector.pokemonapp.domain.entities.Pokemon

interface GetPokemonDetailUseCase {
    suspend operator fun invoke(name: String): Pokemon
}
