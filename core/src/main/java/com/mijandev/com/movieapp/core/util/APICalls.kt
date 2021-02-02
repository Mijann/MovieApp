package com.mijandev.com.movieapp.core.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.mijandev.com.movieapp.core.data.model.Resource
import kotlinx.coroutines.Dispatchers

/**
 * Created by Mohammad Hamizan on 31/1/2021.
 */
/**
 * Generic function for liveData to perform API calls
 */
fun <A> performGetOperation(networkCall: suspend () -> Resource<A>): LiveData<Resource<A>> =
    liveData(Dispatchers.IO) {
        emit(Resource.loading())

        val responseStatus = networkCall.invoke()
        if (responseStatus.status == Resource.Status.SUCCESS) {
            emit(Resource.success(responseStatus.data!!))

        } else if (responseStatus.status == Resource.Status.ERROR) {
            emit(Resource.error(responseStatus.message!!))
        }
    }