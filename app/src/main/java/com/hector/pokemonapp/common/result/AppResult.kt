package com.hector.pokemonapp.common.result

import com.hector.pokemonapp.common.exception.AppError

sealed class AppResult<out T> {
    data class Success<T>(
        val data: T,
    ) : AppResult<T>()

    data class Error(
        val value: AppError,
    ) : AppResult<Nothing>()
}
