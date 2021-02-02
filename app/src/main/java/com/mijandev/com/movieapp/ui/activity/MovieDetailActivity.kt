package com.mijandev.com.movieapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.mijandev.com.movieapp.R
import com.mijandev.com.movieapp.core.data.model.Resource
import com.mijandev.com.movieapp.core.util.*
import com.mijandev.com.movieapp.databinding.ActivityMovieDetailBinding
import com.mijandev.com.movieapp.ui.viewmodel.MainViewModel
import com.squareup.picasso.Picasso
import org.koin.android.ext.android.inject

/**
 * Created by Mohammad Hamizan on 1/2/2021.
 */
/**
 * Movie Detail Activity (Movie Detail Screen)
 */

class MovieDetailActivity : AppCompatActivity() {

    private val binding by contentView<MovieDetailActivity, ActivityMovieDetailBinding>(
        R.layout.activity_movie_detail
    )

    private val mainViewModel: MainViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        makeStatusBarTransparent()

        ViewCompat.setOnApplyWindowInsetsListener(binding.constraintLayout) { _, insets ->
            binding.constraintLayout.setMarginTop(insets.systemWindowInsetTop)
            insets.consumeSystemWindowInsets()
        }

        if (intent.hasExtra(AppConstants.INTENT_MOVIE_ID)) {
            val movie_id = intent.getLongExtra(AppConstants.INTENT_MOVIE_ID, 0)

            mainViewModel.retrieveMovie(movie_id)

        }

        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        binding.bookButton.setOnClickListener {
            startActivity(Intent(this, BookMovieActivity::class.java))
        }

        setupObservable()
    }

    private fun setupObservable() {
        mainViewModel.movieResponse?.observe(this, {

            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.movieDetailLinearLayout.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                }
                Resource.Status.SUCCESS -> {

                    if (it.data != null) {
                        if (it.data!!.posterPath != null && !it.data!!.posterPath!!.startsWith("http")) {
                            it.data!!.posterPath =
                                "${APIConstants.IMAGE_URL}${it.data!!.posterPath}"
                        }
                        Picasso.get().load(it.data!!.posterPath).into(binding.imageView)
                        binding.titleTextView.text = it.data!!.title
                        binding.overviewTextView.text = it.data!!.overview

                        var genres = ArrayList<String>()
                        it.data!!.genres.forEach { genre -> genres.add(genre.name) }

                        if (genres.isNotEmpty())
                            binding.genresTagView.tags = genres

                        binding.durationTextView.text = "${it.data!!.runtime} mins"
                        binding.languageTextView.text = it.data!!.original_language
                        binding.movieDetailLinearLayout.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE

                    } else {
                        binding.progressBar.visibility = View.GONE
                        assignCurrentMovie()
                    }

                }
                Resource.Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    assignCurrentMovie()
                }
            }
        })
    }

    private fun assignCurrentMovie() {
        if(mainViewModel.movie != null) {
            if (mainViewModel.movie!!.posterPath != null && !mainViewModel.movie!!.posterPath!!.startsWith("http")) {
                mainViewModel.movie!!.posterPath =
                    "${APIConstants.IMAGE_URL}${mainViewModel.movie!!.posterPath}"
            }
            Picasso.get().load(mainViewModel.movie!!.posterPath).into(binding.imageView)
            binding.titleTextView.text = mainViewModel.movie!!.title
            binding.overviewTextView.text = mainViewModel.movie!!.overview
            binding.languageTextView.text = mainViewModel.movie!!.original_language
            binding.durationTextView.visibility = View.GONE
            binding.movieDetailLinearLayout.visibility = View.VISIBLE
        }
    }
}