package com.mijandev.com.movieapp.core.data.repository

import com.mijandev.com.movieapp.core.data.datasource.MovieDataSource
import com.mijandev.com.movieapp.core.util.AppConstants
import com.mijandev.com.movieapp.core.util.performGetOperation

/**
 * Created by Mohammad Hamizan on 30/1/2021.
 */
/**
 * Movie API Repository for calling API requests (Eg. retrieveMovies, retrieveMovie)
 */
class MovieAPIRepository(private val movieDataSource: MovieDataSource) {
    fun retrieveMovies(
        page: Long = AppConstants.DEFAULT_PAGE_NO,
        sort_by: String = AppConstants.DEFAULT_SORT_BY
    ) =
        performGetOperation(
            networkCall = { movieDataSource.retrieveMovies(page, sort_by) }
        )

    fun retrieveMovie(id: Long = 0) = performGetOperation(
        networkCall = { movieDataSource.retrieveMovie(id) }
    )
}