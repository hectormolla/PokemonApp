package com.hector.pokemonapp.data.base

import android.content.res.Resources
import androidx.compose.ui.res.stringResource
import com.hector.pokemonapp.R
import com.hector.pokemonapp.common.exception.AppException
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

private fun processException(e: Throwable): AppException = when(e) {
    is HttpRequestTimeoutException, is ResponseException ->
        AppException(
            resMessageId = R.string.error_server,
            msg = e.localizedMessage,
            cause = e
        )
    is IOException ->
        AppException(
            resMessageId = R.string.error_offline,
            msg = e.localizedMessage,
            cause = e
        )
    else -> {
        AppException(
            resMessageId = R.string.error_unknown,
            msg = e.localizedMessage,
            cause = e
        )
    }
}