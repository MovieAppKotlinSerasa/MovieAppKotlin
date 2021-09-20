package com.example.movieapp.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.movieapp.model.Movie

class SearchDiffCallback: DiffUtil.ItemCallback<Movie>() {

    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}