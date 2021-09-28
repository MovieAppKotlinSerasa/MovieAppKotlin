package com.example.movieapp.view

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.adapter.FavoritesAdapter
import com.example.movieapp.databinding.FavoritesFragmentBinding
import com.example.movieapp.model.Movie
import com.example.movieapp.view_model.FavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.example.movieapp.adapter.SpacesItemDecoration
import com.example.movieapp.utils.checkInternet
import com.example.movieapp.utils.showMessage


@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.favorites_fragment) {

    companion object {
        fun newInstance() = FavoritesFragment()
    }

    private lateinit var viewModel: FavoritesViewModel
    private lateinit var _binding: FavoritesFragmentBinding
    private var searchTitle: String = ""
    private var isLoading: Boolean = false

    private val adapter = FavoritesAdapter(false) { descriptionClick, removeClick ->
        if(descriptionClick != null) {
            if(requireActivity().checkInternet()) {
                MovieDetailFragment.newInstance(descriptionClick.id)
                    .show(parentFragmentManager, "dialog_movie_detail")
            } else {
                requireActivity().showMessage(requireView(), "Sem conexão com a internet")
            }
        }
        if(removeClick != null) {
            viewModel.removeMovie(removeClick.id)
        }
    }

    private val observerMovie = Observer<List<Movie>> {
        adapter.updateMovies(it)
        _binding.animationLoading.visibility = View.INVISIBLE
        _binding.animationLoadingDarkMode.visibility = View.INVISIBLE
        _binding.FavoritesRecyclerView.visibility = View.VISIBLE
        _binding.imageViewDoYourSearch.visibility = View.INVISIBLE
        _binding.textViewDoYourSearch.visibility = View.INVISIBLE
        isLoading = false
        if(it.isEmpty()) {
            _binding.imageViewDoYourSearch.visibility = View.VISIBLE
            _binding.textViewDoYourSearch.visibility = View.VISIBLE
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FavoritesFragmentBinding.bind(view)

        viewModel = ViewModelProvider(this).get(FavoritesViewModel::class.java)

        viewModel.movies.observe(viewLifecycleOwner, observerMovie)
        viewModel.getMovies()
        isLoading = true

        when (requireContext().resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> {_binding.animationLoadingDarkMode.visibility = View.VISIBLE}
            Configuration.UI_MODE_NIGHT_NO -> {_binding.animationLoading.visibility = View.VISIBLE}
        }

        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.normal_padding)

        _binding.FavoritesRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        _binding.FavoritesRecyclerView.adapter = adapter
        _binding.FavoritesRecyclerView.addItemDecoration(SpacesItemDecoration(spanCount = 3, spacing = spacingInPixels, includeEdge = true))

        setupButtonsEvents()
    }


    private fun setupButtonsEvents() {

        _binding.searchTextLayout.setEndIconOnClickListener{
            if(!isLoading) {
                viewModel.fetchFavorites(searchTitle)
            }
            val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(_binding.searchEditText.windowToken, 0)
            _binding.searchEditText.clearFocus()
            _binding.searchEditText.isCursorVisible = false
        }

        _binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(stringToFilter: CharSequence?, p1: Int, count: Int, p3: Int) {}

            override fun onTextChanged(stringToFilter: CharSequence?, p1: Int, p2: Int, count: Int) {
                stringToFilter?.let { text ->
                    if (text.length > 2) {
                        searchTitle = text.toString()
                    } else {
                        searchTitle = ""
                    }
                }
            }

            override fun afterTextChanged(stringToFilter: Editable?) {}

        })
        _binding.searchEditText.isSingleLine = true
        _binding.searchEditText.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                if(!isLoading) {
                    viewModel.fetchFavorites(searchTitle)
                }
                val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(v.windowToken, 0)
                v.clearFocus()
                _binding.searchEditText.isCursorVisible = false
                return@OnKeyListener true
            }
            false
        })
    }

}