package com.hector.pokemonapp.presentation.common.views

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hector.pokemonapp.presentation.common.utils.withShimmerEffect

@Composable
internal fun PokemonItemPlaceholderView() {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = 8.dp,
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(130.dp)
            .padding(horizontal = 10.dp, vertical = 5.dp),
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .withShimmerEffect(),
        )
    }
}
