package com.hector.pokemonapp.presentation.features.pokemonList

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hector.pokemonapp.presentation.common.navigation.Destination
import com.hector.pokemonapp.presentation.common.navigation.Route
import com.hector.pokemonapp.presentation.features.pokemonList.views.PokemonListScreenView
import com.hector.pokemonapp.presentation.theme.PokemonTheme
import org.koin.androidx.compose.koinViewModel

object PokemonListScreenRoute : Route {
    override val domain: String
        get() = "pokemon_list"
    override val parametersKeyList: List<String>
        get() = emptyList()
}

object PokemonListScreenDestination : Destination {
    override val value: String
        get() = PokemonListScreenRoute.route
}

internal fun NavGraphBuilder.addPokemonListScreenNavigation() {
    with(PokemonListScreenRoute) {
        composable(route = route) {
            PokemonTheme {
                val viewModel: PokemonListScreenViewModel = koinViewModel()
                PokemonListScreenView(viewModel = viewModel)
            }
        }
    }
}
