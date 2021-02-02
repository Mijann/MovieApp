package com.mijandev.com.movieapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.mijandev.com.movieapp.core.data.model.Movie
import com.mijandev.com.movieapp.core.data.model.MovieAPIResponse
import com.mijandev.com.movieapp.core.data.model.MoviesAPIResponse
import com.mijandev.com.movieapp.core.data.model.Resource
import com.mijandev.com.movieapp.core.data.repository.MovieAPIRepository
import com.mijandev.com.movieapp.core.util.AppConstants

/**
 * Created by Mohammad Hamizan on 31/1/2021.
 */
/**
 * Main ViewModel
 * Handle movies functions (Eg, retrieve movies, retrieve single movie detail by id)
 */
class MainViewModel(private val movieAPIRepository: MovieAPIRepository) : ViewModel() {

    val _moviesResponse = MutableLiveData<Map<String, Any>>()
    val moviesResponse: LiveData<Resource<MoviesAPIResponse>>? = _moviesResponse.switchMap {
        val page = if(it.isNullOrEmpty()) AppConstants.DEFAULT_PAGE_NO else it["page"].toString().toLong()
        val sortBy = if(it.isNullOrEmpty()) AppConstants.DEFAULT_SORT_BY else it["sort_by"].toString()
        movieAPIRepository.retrieveMovies(page, sortBy)
    }

    var movie: Movie? = null

    val _movieResponse = MutableLiveData<Long?>()
    val movieResponse: LiveData<Resource<MovieAPIResponse>>? = _movieResponse.switchMap {
        val id = it ?: 1
        movieAPIRepository.retrieveMovie(id!!.toLong())
    }


    init {
        loadMovies()
    }

    fun loadMovies(page: Long = AppConstants.DEFAULT_PAGE_NO, sortBy: String = AppConstants.DEFAULT_SORT_BY) {
        var mapParam = HashMap<String, Any>()
        mapParam["page"] = page
        mapParam["sort_by"] = sortBy
        _moviesResponse.value = mapParam
    }

    fun retrieveMovie(id: Long = 1) {
        _movieResponse.value = id
    }

    fun isLastPage(): Boolean {
        if (moviesResponse!!.value != null &&
            moviesResponse!!.value!!.data != null &&
            moviesResponse!!.value!!.data!!.results.isNotEmpty()
        ) {
            return moviesResponse!!.value!!.data!!.isLastPage
        }

        return true
    }

}