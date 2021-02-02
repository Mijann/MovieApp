package com.mijandev.com.movieapp.core.data.service

import com.mijandev.com.movieapp.core.data.model.MovieAPIResponse
import com.mijandev.com.movieapp.core.data.model.MoviesAPIResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Mohammad Hamizan on 30/1/2021.
 */
/**
 * Movie REST API
 */
interface MovieApiService {
    @GET("discover/movie")
    suspend fun retrieveMovies(@Query("page") page: Long,@Query("primary_release_date.lte") primary_release_date: String = "2016-12-31", @Query("sort_by") sort_by: String): Response<MoviesAPIResponse>

    @GET("movie/{id}")
    suspend fun retrieveMovie(@Path("id") id: Long): Response<MovieAPIResponse>
}