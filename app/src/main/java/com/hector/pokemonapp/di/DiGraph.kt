package com.hector.pokemonapp.di

import org.koin.core.context.startKoin
import org.koin.core.logger.Level

fun initDiGraph() = startKoin {
    printLogger(Level.ERROR)
    modules(diModules)
}
