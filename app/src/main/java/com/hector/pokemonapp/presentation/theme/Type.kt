package com.hector.pokemonapp.presentation.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val h1
    get() = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
    )

val regularTextBold
    get() = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
    )

val regularText
    get() = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
    )
