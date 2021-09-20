package com.example.movieapp.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.HomeActivity
import com.example.movieapp.R
import com.example.movieapp.adapter.GenreAdapter
import com.example.movieapp.adapter.MovieAdapter
import com.example.movieapp.databinding.MovieFragmentBinding
import com.example.movieapp.model.Genre
import com.example.movieapp.model.GenreResult
import com.example.movieapp.model.Movie
import com.example.movieapp.model.MovieResult
import com.example.movieapp.view_model.MovieViewModel
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint

import com.google.android.material.navigation.NavigationView


@AndroidEntryPoint
class MovieFragment : Fragment(R.layout.movie_fragment) {

    companion object {
        fun newInstance() = MovieFragment()
    }

    private lateinit var viewModel: MovieViewModel
    private lateinit var binding: MovieFragmentBinding
    private var selectedMovie: MovieResult? = null
    private var listOfListMovies: MutableList<List<Movie>> = mutableListOf()
    private var listOfGenres: List<Genre> = emptyList()
    private lateinit var navView: NavigationView
    private val genreAdapter = GenreAdapter(::onClickItem)

    private fun onClickItem(movie: Movie) {
        MovieDetailFragment.newInstance(movie.id).show(parentFragmentManager, "dialog_movie_detail")

    }

    private val observeCurrentUser = Observer<FirebaseUser> {
        navView = (requireActivity() as HomeActivity).findViewById(R.id.nav_view)
        val headerView = navView.getHeaderView(0)
        val navUserEmail = headerView.findViewById<TextView>(R.id.textViewUserEmail)
        navUserEmail.text = it.email
    }

    private val observerListOfGenres = Observer<HashMap<Genre, List<Movie>?>> { genreWithMovies ->
        genreAdapter.update(genreWithMovies)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = MovieFragmentBinding.bind(view)
        viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        viewModel.currentUser.observe(viewLifecycleOwner, observeCurrentUser)
        viewModel.listOfGenres.observe(viewLifecycleOwner, observerListOfGenres)
//        viewModel.getMovies()
        viewModel.fetchCurrentUser()
        viewModel.getListOfGenres()

        binding.moviesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.moviesRecyclerView.adapter = genreAdapter
    }
}