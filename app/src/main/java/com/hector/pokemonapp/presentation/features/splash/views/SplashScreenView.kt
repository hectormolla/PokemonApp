package com.hector.pokemonapp.presentation.features.splash.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.hector.pokemonapp.R
import com.hector.pokemonapp.presentation.common.animations.linearAnimationSpec
import com.hector.pokemonapp.presentation.common.animations.scaleAndFadeIn
import com.hector.pokemonapp.presentation.features.splash.SplashScreenViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SplashScreenView(
    viewModel: SplashScreenViewModel,
) {
    val lottieComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.pokeball_anim))
    val progress by animateLottieCompositionAsState(lottieComposition)
    val splashDuration = 2000
    var imageVisible by remember { mutableStateOf(false) }
    val imageAlpha: Float by animateFloatAsState(
        targetValue = if (imageVisible) 1f else 0f,
        animationSpec = linearAnimationSpec(durationInMillis = splashDuration / 2),
    )
    LaunchedEffect(true) {
        imageVisible = true
        delay(splashDuration.toLong())
        imageVisible = false
        delay(splashDuration.toLong() / 2)
        viewModel.splashFinished()
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 28.dp)
                .offset(y = (-200).dp),
        ) {
            AnimatedVisibility(
                visible = imageVisible,
                enter = scaleAndFadeIn(durationInMillis = splashDuration / 2),
                exit = fadeOut(animationSpec = linearAnimationSpec(durationInMillis = splashDuration / 2)),
            ) {
                Image(
                    modifier = Modifier.fillMaxWidth(),
                    painter = painterResource(R.drawable.pokemon_logo),
                    contentDescription = null,
                )
            }
        }

        LottieAnimation(
            modifier = Modifier
                .size(200.dp)
                .alpha(imageAlpha),
            composition = lottieComposition,
            progress = { progress },
        )
    }
}
