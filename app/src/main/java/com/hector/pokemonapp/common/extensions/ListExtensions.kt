package com.hector.pokemonapp.common.extensions

fun List<String>.summarize(): String {
    return when (this.size) {
        0 -> "No type available"
        1 -> this.first()
        else -> "${this.take(this.size - 1).joinToString()} " +
            "and " +
            "${this.last()}."
    }
}
