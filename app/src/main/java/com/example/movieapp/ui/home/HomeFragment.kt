package com.example.movieapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.adapter.MovieAdapter
import com.example.movieapp.databinding.MainFragmentBinding
import com.example.movieapp.databinding.MovieFragmentBinding
import com.example.movieapp.model.MoviesData
import com.example.movieapp.view.ui.home.HomeViewModel
import com.example.movieapp.view_model.MovieViewModel
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var userEmailTextView: TextView
    private lateinit var homeViewModel: HomeViewModel
//    private var _binding: MainFragmentBinding? = null
    private var _binding: MovieFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    val observerSignOut = Observer<Boolean> {isSignedIn ->
        if (!isSignedIn) {
            requireActivity().finish()
        }
    }

    private lateinit var viewModel: MovieViewModel
    private var selectedMovie: MoviesData? = null
    private val adapter = MovieAdapter()

    val observerSignedUser = Observer<FirebaseUser> { user ->
        userEmailTextView.apply {
            text = user.email
        }
    }

    private val observerMovie = Observer<MoviesData> {
        adapter.updateMovies(it.results)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        _binding = MainFragmentBinding.inflate(inflater, container, false)
        _binding = MovieFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = MovieFragmentBinding.bind(view)
        viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        viewModel.movies.observe(viewLifecycleOwner, observerMovie)
        viewModel.getMovies()

        _binding!!.moviesRecyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        _binding!!.moviesRecyclerView.adapter = adapter
//        val textView: TextView = binding.message
//        homeViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
//
//        userEmailTextView = view.findViewById(R.id.message)
//
//        homeViewModel.isSignedIn.observe(viewLifecycleOwner, observerSignOut)
//        homeViewModel.user.observe(viewLifecycleOwner, observerSignedUser)
//
//        _binding?.buttonLogOut?.setOnClickListener {
//            homeViewModel.signOut()
//        }

//        loadUserData()
    }

    fun loadUserData() {
        homeViewModel.fetchCurrentUser()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}