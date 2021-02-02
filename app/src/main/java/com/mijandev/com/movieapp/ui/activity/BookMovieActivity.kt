package com.mijandev.com.movieapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.ViewCompat
import com.mijandev.com.movieapp.R
import com.mijandev.com.movieapp.core.util.AppConstants
import com.mijandev.com.movieapp.core.util.contentView
import com.mijandev.com.movieapp.core.util.makeStatusBarTransparent
import com.mijandev.com.movieapp.core.util.setMarginTop
import com.mijandev.com.movieapp.databinding.ActivityBookMovieBinding
import com.mijandev.com.movieapp.databinding.ActivityMainBinding
/**
 * Created by Mohammad Hamizan on 1/2/2021.
 */
/**
 * Book Movie Activity (Booking Screen)
 */
class BookMovieActivity : AppCompatActivity() {

    private val binding by contentView<BookMovieActivity, ActivityBookMovieBinding>(
        R.layout.activity_book_movie
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        makeStatusBarTransparent()

        ViewCompat.setOnApplyWindowInsetsListener(binding.webview) { _, insets ->
            binding.webview.setMarginTop(insets.systemWindowInsetTop)
            insets.consumeSystemWindowInsets()
        }

        binding.webview.loadUrl(AppConstants.BOOK_MOVIE_URL)
    }
}