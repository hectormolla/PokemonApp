package com.hector.pokemonapp.common.exception

import com.hector.pokemonapp.R

sealed class AppError(exception: Throwable) :
    Exception(exception.localizedMessage, exception.cause) {
    abstract val messageResId: Int
    data class Server(
        val exception: Throwable,
        override val messageResId: Int = R.string.error_server,
    ) : AppError(exception)
    data class Offline(
        val exception: Throwable,
        override val messageResId: Int = R.string.error_offline,
    ) : AppError(exception)
    data class Unknown(
        val exception: Throwable,
        override val messageResId: Int = R.string.error_unknown,
    ) : AppError(exception)
}
