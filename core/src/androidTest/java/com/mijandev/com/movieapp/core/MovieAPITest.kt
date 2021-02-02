package com.mijandev.com.movieapp.core

import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import com.mijandev.com.movieapp.core.data.model.Resource
import com.mijandev.com.movieapp.core.data.repository.MovieAPIRepository
import com.mijandev.com.movieapp.core.di.coreDataModule
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import org.koin.test.inject

/**
 * Created by Mohammad Hamizan on 30/1/2021.
 */
class MovieAPITest : KoinTest {

    private val movieAPIRepository: MovieAPIRepository by inject()

    init {
        configureKoin()
    }

    //Start Koin configuration
    private fun configureKoin() {
        startKoin {
            androidContext(InstrumentationRegistry.getInstrumentation().targetContext)
            modules(coreDataModule)
        }
    }

    @Test
    fun retrieveMovies() {
        runBlocking {
            val moviesResponse = movieAPIRepository.retrieveMovies()
            Assert.assertNotEquals(moviesResponse.value?.status, Resource.Status.ERROR)
        }
    }

    @Test
    fun retrieveMovie() {
        runBlocking {
            val movieResponse = movieAPIRepository.retrieveMovie(328111)
            Assert.assertNotEquals(movieResponse.value?.status, Resource.Status.ERROR)
        }
    }
}