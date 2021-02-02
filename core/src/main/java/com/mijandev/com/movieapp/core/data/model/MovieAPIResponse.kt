package com.mijandev.com.movieapp.core.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Mohammad Hamizan on 30/1/2021.
 */
/**
 * Single Movie API Response
 */
data class MovieAPIResponse (
    var id: Long,
    var title: String,
    @SerializedName("poster_path")
    var posterPath: String?,
    @SerializedName("backdrop_path")
    var backdropPath: String?,
    var popularity: Double,
    var overview: String,
    val genres: List<Genre>,
    val runtime: Long,
    val original_language: String
)