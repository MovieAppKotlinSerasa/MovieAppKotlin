package com.example.movieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.movieapp.R
import com.example.movieapp.databinding.ItemFavoritesOfflineBinding
import com.example.movieapp.databinding.ItemFavoritosBinding
import com.example.movieapp.model.Movie

class FavoritesAdapter (private val isOffline: Boolean, val onClick : (Movie?, Movie?) -> Unit) : RecyclerView.Adapter<FavoritesViewHolder>() {

    private var listOfMovies: MutableList<Movie> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val view: View
        if(isOffline) {
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_favorites_offline, parent, false)
        } else {
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_favoritos, parent, false)
        }
        return FavoritesViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        listOfMovies[position].apply {
            holder.bind(this, isOffline)
            holder.itemView.findViewById<CardView>(R.id.cardViewMovie).setOnClickListener{
                onClick(this, null)
            }
            if(!isOffline) {
                holder.itemView.findViewById<ImageView>(R.id.RemoveFavoriteImageView)
                    .setOnClickListener {
                        onClick(null, this)
                    }
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

//    private var bindingOnline: ItemFavoritosBinding = ItemFavoritosBinding.bind(itemView)
//    private var bindingOffline: ItemFavoritesOfflineBinding = ItemFavoritesOfflineBinding.bind(itemView)

    fun bind(result: Movie, isOffline: Boolean) {
        if(isOffline) {
            val bindingOffline: ItemFavoritesOfflineBinding = ItemFavoritesOfflineBinding.bind(itemView)
            bindingOffline.movieTitleTextView.text = result.title
        } else {
            val bindingOnline: ItemFavoritosBinding = ItemFavoritosBinding.bind(itemView)
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w342${result.poster_path}")
                .transform(CenterCrop())
                .into(bindingOnline.itemMoviePosterImageView)
            bindingOnline.movieTitleTextView.text = result.title
        }

    }


}