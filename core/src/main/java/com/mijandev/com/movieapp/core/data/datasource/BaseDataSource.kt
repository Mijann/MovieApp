package com.mijandev.com.movieapp.core.data.datasource

import com.mijandev.com.movieapp.core.data.model.Resource
import retrofit2.Response

/**
 * Created by Mohammad Hamizan on 30/1/2020.
 */
/**
 * Base class for Data Source with constant functions (Eg. getResult, error)
 */
abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Resource.success(body)
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {

            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Resource<T> {
        return Resource.error("Network call has failed for a following reason: $message")
    }

}