package com.hector.pokemonapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.hector.pokemonapp.presentation.common.navigation.Navigator
import com.hector.pokemonapp.presentation.common.navigation.NavigatorImpl
import com.hector.pokemonapp.presentation.features.pokemonList.addPokemonListScreenNavigation
import com.hector.pokemonapp.presentation.features.pokemonsDetails.addPokemonDetailsScreenNavigation
import com.hector.pokemonapp.presentation.features.splash.SplashScreenDestination
import com.hector.pokemonapp.presentation.features.splash.addSplashScreenNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { AppContent() }
    }
}

@Composable
internal fun AppContent() {
    val navController = rememberNavController()
    val navigator = NavigatorImpl(navController = navController)
    CompositionLocalProvider(LocalNavigator provides navigator) {
        NavHost(
            navController = navController,
            startDestination = SplashScreenDestination.value,
        ) {
            addSplashScreenNavigation()
            addPokemonListScreenNavigation()
            addPokemonDetailsScreenNavigation()
        }
    }
}

internal val LocalNavigator: ProvidableCompositionLocal<Navigator> =
    staticCompositionLocalOf { error("The local navigator has not been initialised") }
