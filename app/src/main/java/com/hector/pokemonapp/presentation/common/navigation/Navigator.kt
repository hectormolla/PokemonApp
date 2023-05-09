package com.hector.pokemonapp.presentation.common.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavOptionsBuilder

interface Navigator {
    fun navigateToDestination(
        destination: Destination,
        options: NavOptionsBuilder.() -> Unit = {},
    )
    fun goBack()
    fun isFirstScreen(): Boolean
}

interface Route {
    val domain: String
    val parametersKeyList: List<String>
    val route: String
        get() {
            val parameters = parametersKeyList
                .joinToString(separator = "/") { "{$it}" }
                .takeIf { it.isNotEmpty() }
                ?.let { "/$it" }
                ?: ""
            return "$domain$parameters"
        }
}

interface Destination {
    val value: String
}

fun NavBackStackEntry.getStringArgument(key: String): String =
    requireNotNull(arguments?.getString(key))
