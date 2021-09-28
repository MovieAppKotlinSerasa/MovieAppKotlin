package com.example.movieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.movieapp.R
import com.example.movieapp.databinding.ItemMovieBinding
import com.example.movieapp.model.Movie

class MovieAdapter(
    private val listOfMovies: List<Movie> = mutableListOf(),
    val onClick: (Movie) -> Unit,
) : RecyclerView.Adapter<MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val positionToCreateInfiniteLoop = position % listOfMovies.size
        listOfMovies[positionToCreateInfiniteLoop].apply {
            holder.bind(this)
            holder.itemView.findViewById<CardView>(R.id.cardViewMovie).setOnClickListener {
                onClick(this)
            }
        }
    }

    override fun getItemCount(): Int = listOfMovies.size * 3

}

class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var binding: ItemMovieBinding = ItemMovieBinding.bind(itemView)

    fun bind(result: Movie) {
        Glide.with(itemView)
            .load("https://image.tmdb.org/t/p/w342${result.poster_path}")
            .transform(CenterCrop())
            .into(binding.itemMoviePosterImageView)
        binding.movieTitleTextView.text = result.title
    }

}