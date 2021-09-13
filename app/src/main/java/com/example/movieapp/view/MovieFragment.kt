package com.example.movieapp.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.HomeActivity
import com.example.movieapp.R
import com.example.movieapp.adapter.MovieAdapter
import com.example.movieapp.databinding.MovieFragmentBinding
import com.example.movieapp.model.Movies
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
    private var selectedMovie: Movies? = null
    private lateinit var navView: NavigationView
    private val adapter = MovieAdapter()

    private val observerMovie = Observer<Movies> {
        adapter.updateMovies(it.results)
    }

    private val observeCurrentUser = Observer<FirebaseUser> {
        navView = (requireActivity() as HomeActivity).findViewById(R.id.nav_view)
        val headerView = navView.getHeaderView(0)
        val navUserEmail = headerView.findViewById<TextView>(R.id.textViewUserEmail)
        navUserEmail.text = it.email
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = MovieFragmentBinding.bind(view)
        viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        viewModel.movies.observe(viewLifecycleOwner, observerMovie)
        viewModel.currentUser.observe(viewLifecycleOwner, observeCurrentUser)
        viewModel.getMovies()
        viewModel.fetchCurrentUser()

        binding.moviesRecyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.moviesRecyclerView.adapter = adapter
    }
}