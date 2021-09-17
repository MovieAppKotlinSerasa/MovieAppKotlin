package com.example.movieapp.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapp.R
import com.example.movieapp.adapter.FavoritesAdapter
import com.example.movieapp.databinding.FavoritesFragmentBinding
import com.example.movieapp.model.Movie
import com.example.movieapp.view_model.FavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.favorites_fragment) {

    companion object {
        fun newInstance() = FavoritesFragment()
    }

    private lateinit var viewModel: FavoritesViewModel
    private lateinit var _binding: FavoritesFragmentBinding
    private val adapter = FavoritesAdapter()

    private val observerMovie = Observer<List<Movie>> {
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