package com.example.movieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.movieapp.R
import com.example.movieapp.databinding.ItemMovieBinding
import com.example.movieapp.model.Movie

class SearchAdapter(val onClick: (Movie) -> Unit): ListAdapter<Movie, SearchMovieViewHolder>(SearchDiffCallback()) {

    private var listOfMovies = mutableListOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchMovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return SearchMovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchMovieViewHolder, position: Int) {
        getItem(position).apply {
            holder.bind(this)
            holder.itemView.findViewById<CardView>(R.id.cardViewMovie).setOnClickListener{
                onClick(this)
            }
        }
    }


    fun updateMovies(movies: List<Movie>, clear: Boolean = false) {
        if(clear) {
            listOfMovies.clear()
        }
        listOfMovies.addAll(movies)
        submitList(listOfMovies.toMutableList())
    }
}

class SearchMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var binding: ItemMovieBinding = ItemMovieBinding.bind(itemView)

    fun bind(result: Movie) {
        Glide.with(itemView)
            .load("https://image.tmdb.org/t/p/w342${result.poster_path}")
            .transform(CenterCrop())
            .into(binding.itemMoviePosterImageView)
        binding.movieTitleTextView.text = result.title
    }
}
