package com.example.movieapp.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapp.R
import com.example.movieapp.adapter.MovieAdapter
import com.example.movieapp.databinding.SearchFragmentBinding
import com.example.movieapp.model.MovieResult
import com.example.movieapp.view_model.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.search_fragment) {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private lateinit var binding: SearchFragmentBinding
    private val adapter = MovieAdapter{

    }
    private lateinit var viewModel: SearchViewModel

//    private val observeMovies = Observer<MovieResult> {
//        adapter.updateMovies(it.results)
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = SearchFragmentBinding.bind(view)
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

//        viewModel.movieResult.observe(viewLifecycleOwner, observeMovies)
        binding.searchRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.searchRecyclerView.adapter = adapter
        setupFilter()
    }

    private fun setupFilter() {
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(stringToFilter: CharSequence?, p1: Int, count: Int, p3: Int) {}

            override fun onTextChanged(stringToFilter: CharSequence?, p1: Int, p2: Int, count: Int) {
                stringToFilter?.let { text ->
                    if (text.length > 2) {
                        viewModel.getFilteredMovies(1, text.toString())
                    }
                }
            }

            override fun afterTextChanged(stringToFilter: Editable?) {}

        })
    }
}