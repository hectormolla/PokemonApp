package com.hector.pokemonapp.common

import com.hector.pokemonapp.domain.entities.Pokemon

class PokemonBuilder {
    private var id: Int = 1
    private var name: String = "Bulbasaur"
    private var height: Int = 10
    private var weight: Int = 50
    private var imageUrl: String = ""
    private var types: List<String> = listOf("poison", "grass")

    fun id(id: Int) = also {
        this.id = id
    }

    fun name(name: String) = also {
        this.name = name
    }

    fun height(height: Int) = also {
        this.height = height
    }
    fun weight(weight: Int) = also {
        this.weight = weight
    }
    fun imageUrl(imageUrl: String) = also {
        this.imageUrl = imageUrl
    }
    fun types(types: List<String>) = also {
        this.types = types
    }

    fun build() = Pokemon(
        id = id,
        name = name,
        height = height,
        weight = weight,
        imageUrl = imageUrl,
        types = types,
    )
}
