package com.hector.pokemonapp.presentation.features.pokemonsDetails.views

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.hector.pokemonapp.presentation.theme.AppColors
import com.hector.pokemonapp.presentation.theme.AppTypography

@Composable
fun InfoItemView(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = title,
            color = AppColors.text,
            style = AppTypography.regularTextBold,
        )
        Text(
            text = subtitle,
            color = AppColors.text,
            style = AppTypography.regularText,
        )
    }
}
