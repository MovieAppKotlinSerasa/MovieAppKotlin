package com.example.movieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.movieapp.R
import com.example.movieapp.databinding.ItemMovieBinding
import com.example.movieapp.model.MoviesData
import com.example.movieapp.model.Results

class MoviesAdapter(): RecyclerView.Adapter<MovieViewHolder>() {

    private var listOfResults: MutableList<Results> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(listOfResults[position])
    }

    override fun getItemCount(): Int = listOfResults.size

    fun updateMovies(movie: List<Results>) {
        listOfResults.clear()
        listOfResults.addAll(movie)
        notifyDataSetChanged()
    }

}

class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var binding: ItemMovieBinding = ItemMovieBinding.bind(itemView)

    fun bind(result: Results) {
        Glide.with(itemView)
            .load("https://image.tmdb.org/t/p/w342${result.posterPath}")
            .transform(CenterCrop())
            .into(binding.itemMoviePosterImageView)
        binding.movieTitleTextView.text = result.original_title
    }
}