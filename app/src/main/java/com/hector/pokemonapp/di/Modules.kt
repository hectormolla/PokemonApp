package com.hector.pokemonapp.di

import android.util.Log
import com.hector.pokemonapp.data.api.PokemonApi
import com.hector.pokemonapp.data.api.PokemonApiImpl
import com.hector.pokemonapp.data.repository.PokemonRepositoryImpl
import com.hector.pokemonapp.domain.repository.PokemonRepository
import com.hector.pokemonapp.domain.usecase.GetPokemonDetailUseCase
import com.hector.pokemonapp.domain.usecase.GetPokemonPaginatedListUseCase
import com.hector.pokemonapp.domain.usecase.impl.GetPokemonDetailUseCaseImpl
import com.hector.pokemonapp.domain.usecase.impl.GetPokemonPaginatedListUseCaseImpl
import com.hector.pokemonapp.presentation.features.pokemonList.PokemonListScreenViewModel
import com.hector.pokemonapp.presentation.features.pokemonsDetails.PokemonDetailsScreenViewModel
import com.hector.pokemonapp.presentation.features.splash.SplashScreenViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val presentationModule = module {
    viewModel { params -> PokemonListScreenViewModel(params.get(), get()) }
    viewModel { params -> PokemonDetailsScreenViewModel(params.get(), get()) }
    viewModel { params -> SplashScreenViewModel(params.get()) }
}

private val domainModule = module {
    factory<GetPokemonDetailUseCase> { GetPokemonDetailUseCaseImpl(get()) }
    factory<GetPokemonPaginatedListUseCase> { GetPokemonPaginatedListUseCaseImpl(get()) }
}

private val dataModule = module {
    factory<PokemonRepository> { PokemonRepositoryImpl(get()) }
}

private val apiModule = module {
    single {
        HttpClient {
            install(HttpTimeout) {
                connectTimeoutMillis = 5000
                requestTimeoutMillis = 5000
                socketTimeoutMillis = 5000
            }
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.v("Ktor", message)
                    }
                }
                level = LogLevel.ALL
            }
        }
    }
    factory<PokemonApi> { PokemonApiImpl(get()) }
}

val diModules = listOf(
    presentationModule,
    domainModule,
    dataModule,
    apiModule,
)
