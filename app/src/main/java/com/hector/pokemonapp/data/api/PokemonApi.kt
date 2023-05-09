package com.hector.pokemonapp.data.api

import com.hector.pokemonapp.data.api.model.PokemonDetailsResponse
import com.hector.pokemonapp.data.api.model.PokemonPageResponse

interface PokemonApi {
    object Info {
        private const val BASE_URL = "https://pokeapi.co/api/v2"

        // Endpoints
        const val ENDPOINT_POKEMON = "$BASE_URL/pokemon"

        // Params
        const val PARAM_LIMIT = "limit"
        const val PARAM_OFFSET = "offset"

        // Other
        const val PAGE_LIMIT = 20
    }

    suspend fun requestPokemonPage(page: Int): PokemonPageResponse
    suspend fun requestPokemonDetails(name: String): PokemonDetailsResponse
}
