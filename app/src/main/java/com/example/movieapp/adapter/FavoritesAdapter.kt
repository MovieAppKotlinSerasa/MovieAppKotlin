package com.example.movieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.movieapp.R
import com.example.movieapp.databinding.ItemFavoritosBinding
import com.example.movieapp.model.Movie

class FavoritesAdapter (val onClick : (Movie) -> Unit) : RecyclerView.Adapter<FavoritesViewHolder>() {

    private var listOfMovies: MutableList<Movie> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favoritos, parent, false)
        return FavoritesViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        listOfMovies[position].apply {
            holder.bind(this)
            holder.itemView.findViewById<ImageView>(R.id.RemoveFavoriteImageView).setOnClickListener {
                onClick(this)
            }
        }
    }

    override fun getItemCount(): Int = listOfMovies.size

    fun updateMovies(movie: List<Movie>) {
        listOfMovies.clear()
        listOfMovies.addAll(movie)
        notifyDataSetChanged()
    }

}

class FavoritesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var binding: ItemFavoritosBinding = ItemFavoritosBinding.bind(itemView)

    fun bind(result: Movie) {
        Glide.with(itemView)
            .load("https://image.tmdb.org/t/p/w342${result.poster_path}")
            .transform(CenterCrop())
            .into(binding.imageView)
        binding.textView4.text = result.title

    }


}