package com.hector.pokemonapp.presentation.features.pokemonsDetails

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hector.pokemonapp.presentation.common.navigation.Destination
import com.hector.pokemonapp.presentation.common.navigation.Route
import com.hector.pokemonapp.presentation.common.navigation.getStringArgument
import com.hector.pokemonapp.presentation.features.pokemonsDetails.views.PokemonDetailsScreenView
import com.hector.pokemonapp.presentation.theme.PokemonTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

object PokemonDetailsScreenRoute : Route {
    const val nameKey: String = "name"

    override val domain: String
        get() = "pokemon_details"
    override val parametersKeyList: List<String>
        get() = listOf(nameKey)
}

data class PokemonDetailsScreenDestination(
    val name: String,
) : Destination {
    override val value: String
        get() = with(PokemonDetailsScreenRoute) {
            "$domain/$name"
        }
}

internal fun NavGraphBuilder.addPokemonDetailsScreenNavigation() {
    with(PokemonDetailsScreenRoute) {
        composable(route = route) { backstackEntry ->
            val name = backstackEntry.getStringArgument(nameKey)

            PokemonTheme {
                val viewModel: PokemonDetailsScreenViewModel = koinViewModel { parametersOf(name) }
                PokemonDetailsScreenView(viewModel = viewModel)
            }
        }
    }
}
