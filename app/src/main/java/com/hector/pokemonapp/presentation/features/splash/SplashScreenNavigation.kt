package com.hector.pokemonapp.presentation.features.splash

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hector.pokemonapp.presentation.common.navigation.Destination
import com.hector.pokemonapp.presentation.common.navigation.Route
import com.hector.pokemonapp.presentation.features.splash.views.SplashScreenView
import com.hector.pokemonapp.presentation.theme.PokemonTheme

object SplashScreenRoute : Route {
    override val domain: String
        get() = "splash"
    override val parametersKeyList: List<String>
        get() = emptyList()
}

object SplashScreenDestination : Destination {
    override val value: String
        get() = SplashScreenRoute.route
}

internal fun NavGraphBuilder.addSplashScreenNavigation() {
    with(SplashScreenRoute) {
        composable(route = route) {
            PokemonTheme {
                SplashScreenView()
            }
        }
    }
}
