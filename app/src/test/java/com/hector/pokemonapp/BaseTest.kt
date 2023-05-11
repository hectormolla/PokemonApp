package com.hector.pokemonapp

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.setMain

abstract class BaseTest {
    init {
        Dispatchers.setMain(Dispatchers.Default)
    }
}