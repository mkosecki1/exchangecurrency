package com.example.exchangecurrency.extention

import com.example.exchangecurrency.network.Result
import retrofit2.Response

suspend fun <T : Any> safeApi(call: suspend () -> Response<T>): Result<T> {
    try {
        val response = listOf(call.invoke())
        return if (response.first().isSuccessful) Result.Success(response.first().body()!!)
        else return Result.Error(response.first().errorBody().toString())
    } catch (e: Exception) {
        return Result.Exception(e)
    }
}