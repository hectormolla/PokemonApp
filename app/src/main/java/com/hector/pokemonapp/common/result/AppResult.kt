package com.hector.pokemonapp.common.result

import com.hector.pokemonapp.common.exception.AppException

sealed class AppResult<out T> {
    data class Success<T>(
        val data: T,
    ) : AppResult<T>()

    data class Error(
        val exception: AppException,
    ) : AppResult<Nothing>()
}
