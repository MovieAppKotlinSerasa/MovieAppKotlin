package com.example.movieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.databinding.ItemGenresBinding
import com.example.movieapp.model.Genre
import com.example.movieapp.model.Movie

class GenreAdapter(private val onClickItem: (Movie) -> Unit) : RecyclerView.Adapter<ItemGenreViewHolder>() {

    private val listOfGenres = mutableListOf<Genre>()
    lateinit var hashMapOfGenres : HashMap<Genre, List<Movie>?>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemGenreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_genres, parent, false)
        return ItemGenreViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemGenreViewHolder, position: Int) {

        listOfGenres[position].let { genre ->
            holder.bind(genre)
            holder.itemView.findViewById<RecyclerView>(R.id.mostPopularMoviesByGenreRecyclerView).apply {
                hashMapOfGenres[genre]?.let { movies ->
                    val adapter = MovieAdapter(movies) {
                        onClickItem(it)
                    }
                    this.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
                    this.adapter = adapter
                }
            }
        }
    }

    override fun getItemCount(): Int = listOfGenres.size

    fun update(hashMapGenres: HashMap<Genre, List<Movie>?>) {
        listOfGenres.clear()
        listOfGenres.addAll(hashMapGenres.keys)
        hashMapOfGenres = hashMapGenres
        notifyDataSetChanged()
    }

}

class ItemGenreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding: ItemGenresBinding = ItemGenresBinding.bind(itemView)

    fun bind(genre: Genre) {
        binding.movieGenreTextView.text = genre.name

//        binding.mostPopularMoviesByGenreRecyclerView.layoutManager = LinearLayoutManager()
    }
}