package com.example.movieapp.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.adapter.MoviesAdapter
import com.example.movieapp.databinding.MovieFragmentBinding
import com.example.movieapp.model.MoviesData
import com.example.movieapp.view_model.MovieViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : Fragment(R.layout.movie_fragment) {

    companion object {
        fun newInstance() = MovieFragment()
    }

    private lateinit var viewModel: MovieViewModel
    private lateinit var binding: MovieFragmentBinding
    private var selectedMovie: MoviesData? = null
    private val adapter = MoviesAdapter()

    private val observerMovie = Observer<MoviesData> {
        adapter.updateMovies(it.results)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = MovieFragmentBinding.bind(view)
        viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        viewModel.movies.observe(viewLifecycleOwner, observerMovie)
        viewModel.getMovies()

        binding.moviesRecyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.moviesRecyclerView.adapter = adapter
    }
}