package com.mijandev.com.movieapp.core.di

import com.google.gson.Gson
import com.mijandev.com.movieapp.core.data.datasource.MovieDataSource
import com.mijandev.com.movieapp.core.data.interceptor.RequestInterceptor
import com.mijandev.com.movieapp.core.data.repository.MovieAPIRepository
import com.mijandev.com.movieapp.core.data.service.MovieApiService
import com.mijandev.com.movieapp.core.util.APIConstants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import com.mijandev.com.movieapp.core.BuildConfig
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Mohammad Hamizan on 30/1/2021.
 */
/**
 * Core data module
 * To inject OkHttpClient, HttpLogging, REST API Service
 */
val coreDataModule = module {
    single {
        createOkHttpClient(get())
    }
    single {
        createHttpLoggingInterceptor()
    }
    single { Gson() }
    single { GsonConverterFactory.create(get()) }

    single { createWebService<MovieApiService>(get(), get(), APIConstants.BASE_URL) }
    single { MovieDataSource(get()) }
    single { MovieAPIRepository(get()) }
}

fun createHttpLoggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }
}

fun createOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(10L, TimeUnit.SECONDS)
        .readTimeout(10L, TimeUnit.SECONDS)
        .addNetworkInterceptor(RequestInterceptor())
        .addInterceptor(httpLoggingInterceptor).build()
}

inline fun <reified T> createWebService(
    okHttpClient: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory,
    url: String
): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(gsonConverterFactory).build()
    return retrofit.create(T::class.java)
}