package com.mijandev.com.movieapp.core.data.model

/**
 * Created by Mohammad Hamizan on 30/1/2021.
 */
/**
 * Movies API Response
 */
data class MoviesAPIResponse (
    val page: Long,
    val results: List<Movie>,
    val total_results: Long,
    val total_pages: Long,
    val isLastPage: Boolean = (page == total_pages)
)