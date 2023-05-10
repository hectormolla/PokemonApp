package com.hector.pokemonapp.data.base

import com.hector.pokemonapp.common.exception.AppError
import com.hector.pokemonapp.common.result.AppResult
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.ResponseException
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

abstract class BaseRepository {
    protected fun <T> repositoryFlow(block: suspend FlowCollector<T>.() -> Unit) = flow {
        try {
            block.invoke(this)
        } catch (e: Exception) {
            throw processException(e)
        }
    }.flowOn(Dispatchers.IO)

    protected suspend fun <T> bg(block: suspend () -> AppResult<T>): AppResult<T> = withContext(Dispatchers.IO) {
        return@withContext try {
            block.invoke()
        } catch (e: Exception) {
            AppResult.Error(processException(e))
        }
    }
}

private fun processException(e: Throwable): AppError = when (e) {
    is HttpRequestTimeoutException, is ResponseException -> AppError.Server(exception = e)
    is IOException -> AppError.Offline(exception = e)
    else -> AppError.Unknown(exception = e)
}
