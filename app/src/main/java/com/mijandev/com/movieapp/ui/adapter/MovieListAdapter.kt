package com.mijandev.com.movieapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mijandev.com.movieapp.core.data.model.Movie
import com.mijandev.com.movieapp.core.util.APIConstants
import com.mijandev.com.movieapp.databinding.LayoutSingleMovieBinding
import com.squareup.picasso.Picasso
/**
 * Created by Mohammad Hamizan on 31/1/2021.
 */
/**
 * Recyclerview Adapter for movies
 * Populate movies data to view
 */
class MovieListAdapter :
    RecyclerView.Adapter<MovieListAdapter.CustomViewHolder>() {

    var movies: MutableList<Movie> = mutableListOf()


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): MovieListAdapter.CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding =
            LayoutSingleMovieBinding.inflate(layoutInflater, parent, false)
        return CustomViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bindTo(getMovie(position), position)
    }

    fun getMovie(position: Int): Movie {
        return movies[position]
    }

    fun setItems(movies: List<Movie>) {
        val startPosition = this.movies.size
        this.movies.addAll(movies)
        notifyItemRangeChanged(startPosition, movies.size)
    }

    override fun getItemCount(): Int = movies.size

    class CustomViewHolder(
        private val binding: LayoutSingleMovieBinding,
    ) : RecyclerView.ViewHolder(
        binding.root
    ) {

        fun bindTo(movie: Movie, position: Int) {
            if (movie.posterPath != null && !movie.posterPath!!.startsWith("http")) {
                movie.posterPath = "${APIConstants.IMAGE_URL}${movie.posterPath}"
            }
            Picasso.get().load(movie.posterPath).into(binding.imageView)
            binding.titleTextView.text = movie.title
            binding.popularityTextView.text = movie.popularity.toString()

        }
    }
}