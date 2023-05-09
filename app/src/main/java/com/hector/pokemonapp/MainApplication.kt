package com.hector.pokemonapp

import android.app.Application
import com.hector.pokemonapp.di.initDiGraph

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initDiGraph()
    }
}
