package com.hector.pokemonapp.data.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

abstract class BaseRepository {
    protected fun <T> repositoryFlow(block: suspend FlowCollector<T>.() -> Unit) = flow {
        try {
            block.invoke(this)
        } catch (e: Exception) {
            // TODO
        }
    }.flowOn(Dispatchers.IO)
}
