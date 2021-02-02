package com.mijandev.com.movieapp.core.data.datasource

import com.mijandev.com.movieapp.core.data.service.MovieApiService

/**
 * Created by Mohammad Hamizan on 30/1/2021.
 */
/**
 * Movie Data Source with its functions (Eg. retrieveMovies, retrieveMovie)
 * To retrieve all movies by page, sorting
 * To retrieve single movie detail by id
 */
class MovieDataSource(private val movieApiService: MovieApiService) : BaseDataSource() {
    suspend fun retrieveMovies(page: Long, sort_by: String) = getResult { movieApiService.retrieveMovies(page, sort_by = sort_by) }
    suspend fun retrieveMovie(id: Long) = getResult { movieApiService.retrieveMovie(id) }
}