package com.mijandev.com.movieapp.core.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by Mohammad Hamizan on 30/1/2021.
 */
/**
 * Movie data class
 */
data class Movie(
    var id: Long,
    var title: String,
    @SerializedName("poster_path")
    var posterPath: String?,
    @SerializedName("backdrop_path")
    var backdropPath: String?,
    var popularity: Double,
    var original_language: String,
    var overview: String
)