package com.hector.pokemonapp.data.api

import com.hector.pokemonapp.data.api.PokemonApi.Info.PAGE_LIMIT
import com.hector.pokemonapp.data.api.model.PokemonDetailsResponse
import com.hector.pokemonapp.data.api.model.PokemonPageResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url

class PokemonApiImpl(
    private val client: HttpClient,
) : PokemonApi {

    override suspend fun requestPokemonPage(page: Int): PokemonPageResponse = client.get {
        url(PokemonApi.Info.ENDPOINT_POKEMON)
        parameter(PokemonApi.Info.PARAM_LIMIT, PAGE_LIMIT)
        parameter(PokemonApi.Info.PARAM_OFFSET, page * PAGE_LIMIT)
    }.body()

    override suspend fun requestPokemonDetails(name: String): PokemonDetailsResponse = client.get {
        url("${PokemonApi.Info.ENDPOINT_POKEMON}/$name")
    }.body()
}
