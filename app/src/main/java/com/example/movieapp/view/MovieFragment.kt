package com.example.movieapp.view

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.HomeActivity
import com.example.movieapp.R
import com.example.movieapp.adapter.GenreAdapter
import com.example.movieapp.databinding.MovieFragmentBinding
import com.example.movieapp.model.Genre
import com.example.movieapp.model.Movie
import com.example.movieapp.utils.checkInternet
import com.example.movieapp.utils.replaceView
import com.example.movieapp.utils.showMessage
import com.example.movieapp.view_model.MovieViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint

const val POPULARITY = "popularity.desc"
const val VOTE_COUNT = "vote_count.desc"
const val VOTE_RATE = "vote_average.desc"
const val RELEASES = "release_date.desc"

@AndroidEntryPoint
class MovieFragment : Fragment(R.layout.movie_fragment) {

    companion object {
        fun newInstance() = MovieFragment()
    }

    private lateinit var viewModel: MovieViewModel
    private lateinit var binding: MovieFragmentBinding
    private lateinit var navView: NavigationView
    private val genreAdapter = GenreAdapter(::onClickItem)
    private var singleItems = arrayOf("Popularidade", "Mais votados", "Melhores avaliações", "Lançamentos")
    private var checkedItem = 0
    private var selectedItem = 0
    private var sortBy = POPULARITY

    private fun onClickItem(movie: Movie?, id: Int?) {
        if (movie != null){
            if(requireActivity().checkInternet()) {
                MovieDetailFragment.newInstance(movie.id)
                    .show(parentFragmentManager, "dialog_movie_detail")
            } else {
                requireActivity().showMessage(requireView(), "Sem conexão com a internet")
            }
        } else if(id != null){
            if(requireActivity().checkInternet()) {
                (requireActivity() as HomeActivity)
                    .replaceView(SearchFragment.newInstance(id, sortBy), R.id.nav_host_fragment_home_container)
            } else {
                requireActivity().showMessage(requireView(), "Sem conexão com a internet")
            }
        }
    }

    private val observeCurrentUser = Observer<FirebaseUser> {
        navView = (requireActivity() as HomeActivity).findViewById(R.id.nav_view)
        val headerView = navView.getHeaderView(0)
        val navUserEmail = headerView.findViewById<TextView>(R.id.textViewUserEmail)
        navUserEmail.text = it.email
    }

    private val observerListOfGenres = Observer<HashMap<Genre, List<Movie>?>> { genreWithMovies ->
        genreAdapter.update(genreWithMovies)

        binding.animationLoading.visibility = View.INVISIBLE
        binding.animationLoadingDarkMode.visibility = View.INVISIBLE
        binding.moviesRecyclerView.visibility = View.VISIBLE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = MovieFragmentBinding.bind(view)
        viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        viewModel.currentUser.observe(viewLifecycleOwner, observeCurrentUser)
        viewModel.listOfGenres.observe(viewLifecycleOwner, observerListOfGenres)

        when (requireContext().resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> {binding.animationLoadingDarkMode.visibility = View.VISIBLE}
            Configuration.UI_MODE_NIGHT_NO -> {binding.animationLoading.visibility = View.VISIBLE}
        }

        viewModel.fetchCurrentUser()
        viewModel.getListOfGenres(sortBy)

        binding.moviesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.moviesRecyclerView.adapter = genreAdapter

        binding.imageViewFilterGenres.setOnClickListener {
            showFilterDialog(requireContext())
        }

    }

    private fun showFilterDialog(context: Context){
        MaterialAlertDialogBuilder(context)
            .setTitle("Filtro")
            .setNegativeButton("Cancelar") { dialog, which ->
                dialog.cancel()
            }
            .setPositiveButton("Ok") { dialog, which ->
                if(requireActivity().checkInternet()) {
                    viewModel.getListOfGenres(sortBy)
                    checkedItem = selectedItem
                    when (context.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
                        Configuration.UI_MODE_NIGHT_YES -> {
                            binding.animationLoadingDarkMode.visibility = View.VISIBLE
                        }
                        Configuration.UI_MODE_NIGHT_NO -> {
                            binding.animationLoading.visibility = View.VISIBLE
                        }
                    }
                    binding.moviesRecyclerView.visibility = View.INVISIBLE
                } else {
                    requireActivity().showMessage(requireView(), "Sem conexão com a internet")
                }
                dialog.dismiss()
            }
            .setSingleChoiceItems(singleItems, checkedItem) { dialog, which ->
                selectedItem = which
                when(which){
                    0 -> sortBy = POPULARITY
                    1 -> sortBy = VOTE_COUNT
                    2 -> sortBy = VOTE_RATE
                    3 -> sortBy = RELEASES
                }
            }
            .show()
    }

}