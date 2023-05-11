package com.hector.pokemonapp.presentation.common.animations

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn

@OptIn(ExperimentalAnimationApi::class)
fun scaleAndFadeIn(
    durationInMillis: Int,
): EnterTransition = scaleIn(
    animationSpec = linearAnimationSpec(durationInMillis = durationInMillis),
) + fadeIn(
    animationSpec = linearAnimationSpec(durationInMillis = durationInMillis),
)

fun <T>linearAnimationSpec(durationInMillis: Int): FiniteAnimationSpec<T> = tween(
    durationMillis = durationInMillis,
    easing = LinearEasing,
)
