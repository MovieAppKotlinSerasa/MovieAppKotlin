package com.example.movieapp.view

import android.content.Context
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
import com.example.movieapp.R
import com.example.movieapp.adapter.FavoritesAdapter
import com.example.movieapp.adapter.SpacesItemDecoration
import com.example.movieapp.databinding.OfflineFragmentBinding
import com.example.movieapp.model.Movie
import com.example.movieapp.model.User
import com.example.movieapp.view_model.OfflineViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OfflineFragment : Fragment(R.layout.offline_fragment) {

    companion object {
        fun newInstance() = OfflineFragment()
    }

    private lateinit var viewModel: OfflineViewModel
    private lateinit var binding: OfflineFragmentBinding
    private var searchTitle: String = ""
    private var adapter = FavoritesAdapter(true){ descriptionClick, removeClick ->
        if(descriptionClick != null) {
            MovieDetailFragment.newInstance(descriptionClick.id).show(parentFragmentManager, "dialog_movie_detail")
        }
    }

    private val observerMovies = Observer<List<Movie>> {
        adapter.updateMovies(it)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = OfflineFragmentBinding.bind(view)
        viewModel = ViewModelProvider(this).get(OfflineViewModel::class.java)

        viewModel.movies.observe(viewLifecycleOwner, observerMovies)

        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.normal_padding)

        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(SpacesItemDecoration(spanCount = 3, spacing = spacingInPixels, includeEdge = true))
        setupButtonsEvents()

    }

    private fun setupButtonsEvents() {

        binding.searchEditText.addTextChangedListener(object : TextWatcher {
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
        binding.searchEditText.isSingleLine = true
        binding.searchEditText.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {

                viewModel.fetchFavorites(searchTitle)

                val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(v.windowToken, 0)
                v.clearFocus()
                binding.searchEditText.isCursorVisible = false
                return@OnKeyListener true
            }
            false
        })
        searchTitle = binding.searchEditText.text.toString()
        viewModel.fetchFavorites(searchTitle)
    }

}