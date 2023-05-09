package com.hector.pokemonapp.presentation.common.animations

import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween

fun <T>linearAnimationSpec(durationInMillis: Int): FiniteAnimationSpec<T> = tween(
    durationMillis = durationInMillis,
    easing = LinearEasing,
)
