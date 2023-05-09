package com.hector.pokemonapp.presentation.common.navigation

import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder

class NavigatorImpl(
    private val navController: NavHostController,
) : Navigator {
    override fun navigateToDestination(
        destination: Destination,
        options: NavOptionsBuilder.() -> Unit,
    ) {
        navController.navigate(destination.value, options)
    }

    override fun goBack() {
        navController.navigateUp()
    }

    override fun isFirstScreen(): Boolean = navController.previousBackStackEntry == null
}
