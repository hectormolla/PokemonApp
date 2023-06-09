package com.hector.pokemonapp.presentation.common.utils

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.hector.pokemonapp.presentation.theme.AppColors

@Composable
fun Modifier.withShimmerEffect(
    initialValue: Float = 0f,
    targetValue: Float = 1000f,
    durationMs: Int = 1200,
    gradientColors: List<Color> = shimmerColorShades(),
): Modifier = composed {
    val transition = rememberInfiniteTransition()
    val translateAnim by transition.animateFloat(
        initialValue = initialValue,
        targetValue = targetValue,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = durationMs, easing = FastOutLinearInEasing),
            RepeatMode.Reverse,
        ),
    )

    background(
        Brush.linearGradient(
            colors = gradientColors,
            start = Offset(10f, 10f),
            end = Offset(translateAnim, translateAnim),
        ),
    )
}

@Composable
private fun shimmerColorShades() = listOf(
    AppColors.primary.copy(0f),
    AppColors.primary.copy(0.1f),
    AppColors.primary.copy(0f),
)
