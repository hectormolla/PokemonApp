package com.hector.pokemonapp.common.exception

class AppException(val resMessageId: Int, msg: String?, cause: Throwable?): Exception(msg, cause)


