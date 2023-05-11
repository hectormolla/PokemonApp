package com.hector.pokemonapp.presentation.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object AppTypography {
    val h1= TextStyle(
            fontWeight = FontWeight.SemiBold,
            fontSize = 28.sp,
        )

    val regularTextBold = TextStyle(
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
        )

    val regularText = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
        )
}

