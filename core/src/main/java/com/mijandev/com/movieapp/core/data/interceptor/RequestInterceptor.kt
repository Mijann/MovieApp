package com.mijandev.com.movieapp.core.data.interceptor

import com.mijandev.com.movieapp.core.util.APIConstants
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by Mohammad Hamizan on 30/1/2021.
 */
/**
 * Custom network interceptor to add api_key to every API call requests
 */
class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url
        val url = originalUrl.newBuilder()
            .addQueryParameter("api_key", APIConstants.API_KEY)
            .build()

        val requestBuilder = originalRequest.newBuilder().url(url)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }

}