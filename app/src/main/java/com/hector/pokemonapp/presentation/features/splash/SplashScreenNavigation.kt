package com.hector.pokemonapp.presentation.features.splash

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hector.pokemonapp.presentation.LocalNavigator
import com.hector.pokemonapp.presentation.common.navigation.Destination
import com.hector.pokemonapp.presentation.common.navigation.Route
import com.hector.pokemonapp.presentation.features.splash.views.SplashScreenView
import com.hector.pokemonapp.presentation.theme.PokemonTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

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
                val navigator = LocalNavigator.current
                val viewModel: SplashScreenViewModel = koinViewModel { parametersOf(navigator) }
                SplashScreenView(viewModel = viewModel)
            }
        }
    }
}
