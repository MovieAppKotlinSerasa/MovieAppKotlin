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
import com.example.movieapp.adapter.SpacesItemDecoration


@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.favorites_fragment) {

    companion object {
        fun newInstance() = FavoritesFragment()
    }

    private lateinit var viewModel: FavoritesViewModel
    private lateinit var _binding: FavoritesFragmentBinding

    private val adapter = FavoritesAdapter(false) { descriptionClick, removeClick ->
        if(descriptionClick != null) {
            MovieDetailFragment.newInstance(descriptionClick.id).show(parentFragmentManager, "dialog_movie_detail")
        }
        if(removeClick != null) {
            viewModel.removeMovie(removeClick.id)
        }
    }

    private val observerMovie = Observer<List<Movie>> {
        adapter.updateMovies(it)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FavoritesFragmentBinding.bind(view)

        viewModel = ViewModelProvider(this).get(FavoritesViewModel::class.java)

        viewModel.movies.observe(viewLifecycleOwner, observerMovie)
        viewModel.getMovies()


        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.normal_padding)

        _binding.FavoritesRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        _binding.FavoritesRecyclerView.adapter = adapter
        _binding.FavoritesRecyclerView.addItemDecoration(SpacesItemDecoration(spanCount = 3, spacing = spacingInPixels, includeEdge = true))


    }

}