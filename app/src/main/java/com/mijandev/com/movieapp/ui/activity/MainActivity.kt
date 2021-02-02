package com.mijandev.com.movieapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.mijandev.com.movieapp.R
import com.mijandev.com.movieapp.core.data.model.Resource
import com.mijandev.com.movieapp.core.ui.recyclerview.RecyclerItemClickListener
import com.mijandev.com.movieapp.core.ui.recyclerview.RecyclerViewPaginator
import com.mijandev.com.movieapp.core.util.AppConstants
import com.mijandev.com.movieapp.core.util.contentView
import com.mijandev.com.movieapp.core.util.makeStatusBarTransparent
import com.mijandev.com.movieapp.core.util.setMarginTop
import com.mijandev.com.movieapp.databinding.ActivityMainBinding
import com.mijandev.com.movieapp.ui.adapter.MovieListAdapter
import com.mijandev.com.movieapp.ui.viewmodel.MainViewModel
import org.koin.android.ext.android.inject

/**
 * Created by Mohammad Hamizan on 30/1/2021.
 */
/**
 * Main Activity (Home Screen)
 */

class MainActivity : AppCompatActivity() {

    private val binding by contentView<MainActivity, ActivityMainBinding>(
        R.layout.activity_main
    )

    private val mainViewModel: MainViewModel by inject()

    private val movieListAdapter: MovieListAdapter = MovieListAdapter()

    private var currentSortBy: String = AppConstants.DEFAULT_SORT_BY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        makeStatusBarTransparent()

        ViewCompat.setOnApplyWindowInsetsListener(binding.swipeRefreshLayout) { _, insets ->
            binding.swipeRefreshLayout.setMarginTop(insets.systemWindowInsetTop)
            insets.consumeSystemWindowInsets()
        }

        initRecyclerView()
        setupObservable()
        initView()
    }

    private fun initRecyclerView() {

        val viewManager = LinearLayoutManager(this)

        binding.moviesRecyclerview.apply {
            layoutManager = viewManager
            adapter = movieListAdapter
            addOnItemTouchListener(
                RecyclerItemClickListener(
                    this@MainActivity,
                    object : RecyclerItemClickListener.OnRecyclerViewItemClickListener {
                        override fun onItemClick(parentView: View, childView: View, position: Int) {
                            var intent = Intent(this@MainActivity, MovieDetailActivity::class.java)
                            intent!!.putExtra(
                                AppConstants.INTENT_MOVIE_ID,
                                movieListAdapter.getMovie(position).id
                            )
                            mainViewModel.movie = movieListAdapter.getMovie(position)
                            startActivity(intent)
                        }
                    })
            )
            addOnScrollListener(object : RecyclerViewPaginator(this) {
                override val isLastPage: Boolean
                    get() = mainViewModel.isLastPage()

                override fun loadMore(page: Long) {
                    mainViewModel.loadMovies(page, currentSortBy)
                }

            })
        }
    }


    private fun initView() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            clearData()
            mainViewModel.loadMovies(AppConstants.DEFAULT_PAGE_NO, currentSortBy)
        }

        val myadapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            resources.getStringArray(R.array.sort_by_selection)
        )
        myadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.sortByDropdown.adapter = myadapter
        binding.sortByDropdown.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when (position) {
                        0 -> {
                            clearData()
                            currentSortBy = AppConstants.DEFAULT_SORT_BY
                            mainViewModel.loadMovies(
                                AppConstants.DEFAULT_PAGE_NO,
                                AppConstants.DEFAULT_SORT_BY
                            )
                        }
                        1 -> {
                            clearData()
                            currentSortBy = AppConstants.ALPHABETICAL_SORT_BY
                            mainViewModel.loadMovies(
                                AppConstants.DEFAULT_PAGE_NO,
                                AppConstants.ALPHABETICAL_SORT_BY
                            )
                        }
                        2 -> {
                            clearData()
                            currentSortBy = AppConstants.RATING_SORT_BY
                            mainViewModel.loadMovies(
                                AppConstants.DEFAULT_PAGE_NO,
                                AppConstants.RATING_SORT_BY
                            )
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

            }
    }

    private fun clearData() {
        movieListAdapter.movies.clear()
        movieListAdapter.notifyDataSetChanged()
    }

    private fun setupObservable() {
        mainViewModel.moviesResponse?.observe(this, {
            when (it.status) {
                Resource.Status.LOADING -> {
                    if (!binding.swipeRefreshLayout.isRefreshing)
                        binding.progressBar.visibility = View.VISIBLE
                }
                Resource.Status.SUCCESS -> {
                    if (it.data!!.results.isNotEmpty()) {
                        movieListAdapter.setItems(it.data!!.results)
                    }
                    binding.swipeRefreshLayout.isRefreshing = false
                    binding.progressBar.visibility = View.GONE
                }
                Resource.Status.ERROR -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    binding.progressBar.visibility = View.GONE
                }
            }
        })
    }

}