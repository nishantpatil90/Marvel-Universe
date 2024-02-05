package com.example.marveluniverse.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <T> safeApiCall(
    apiCall: suspend () -> T,
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
): Result<T> {
    return withContext(dispatcher) {
        try {
            Result.success(apiCall.invoke())
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            Result.failure(throwable)
        }
    }
}
