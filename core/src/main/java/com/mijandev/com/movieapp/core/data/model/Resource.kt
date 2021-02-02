package com.mijandev.com.movieapp.core.data.model

/**
 * Created by Mohammad Hamizan on 30/1/2020.
 */
/**
 * Created by Mohammad Hamizan on 18/1/2021.
 */
/**
 * Base class for Resource for dynamic API response which cater SUCCESS, LOADING, ERROR result
 */
data class Resource<out T>(val status: Status, val data: T?, val message: String?) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T? = null): Resource<T> {
            return Resource(Status.ERROR, data, message)
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}