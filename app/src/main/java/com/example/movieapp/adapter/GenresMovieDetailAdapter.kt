package com.example.movieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.databinding.ItemChipBinding
import com.example.movieapp.model.Genre

class GenresMovieDetailAdapter(private val listOfGenres: List<Genre> = mutableListOf()) : RecyclerView.Adapter<GenreViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chip, parent, false)
        return GenreViewHolder(view)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        listOfGenres[position].apply {
            holder.bind(this)
        }
    }

    override fun getItemCount(): Int = listOfGenres.size

}

class GenreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var binding: ItemChipBinding = ItemChipBinding.bind(itemView)
    fun bind(genre: Genre){
        binding.chipGenre.text = genre.name
    }
}