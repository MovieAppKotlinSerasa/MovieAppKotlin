package com.example.movieapp.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.adapter.FavoritesAdapter
import com.example.movieapp.adapter.MovieAdapter
import com.example.movieapp.databinding.FavoritesFragmentBinding
import com.example.movieapp.databinding.MovieFragmentBinding
import com.example.movieapp.model.Movies
import com.example.movieapp.model.Results
import com.example.movieapp.view_model.FavoritesViewModel
import com.example.movieapp.view_model.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.favorites_fragment) {

    companion object {
        fun newInstance() = FavoritesFragment()
    }

    private lateinit var viewModel: FavoritesViewModel
    private lateinit var _binding: FavoritesFragmentBinding
    private val adapter = FavoritesAdapter()

    private val observerMovie = Observer<List<Results>> {
        adapter.updateMovies(it)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FavoritesFragmentBinding.bind(view)

        viewModel = ViewModelProvider(this).get(FavoritesViewModel::class.java)

        viewModel.movies.observe(viewLifecycleOwner, observerMovie)
        viewModel.getMovies()

        _binding.FavoritesRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        _binding.FavoritesRecyclerView.adapter = adapter

    }

}