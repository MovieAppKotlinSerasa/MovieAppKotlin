package com.example.movieapp.view

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.HomeActivity
import com.example.movieapp.R
import com.example.movieapp.adapter.SearchAdapter
import com.example.movieapp.adapter.SpacesItemDecoration
import com.example.movieapp.databinding.SearchFragmentBinding
import com.example.movieapp.model.MovieResult
import com.example.movieapp.view_model.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.search_fragment) {

    companion object {
        fun newInstance(genreId: Int, sortBy: String) : SearchFragment {
            return SearchFragment().apply {
                val args = Bundle()
                args.putInt("genre_key", genreId)
                args.putString("sortBy_key", sortBy)
                this.arguments = args
            }
        }
    }

    private var genreId : Int? = null
    private var sortBy: String? = null
    private lateinit var binding: SearchFragmentBinding
    private lateinit var viewModel: SearchViewModel
    private var searchString = ""
    private var clearList = false
    private var page = 1
    private val adapter = SearchAdapter{
        MovieDetailFragment.newInstance(it.id).show(parentFragmentManager, "dialog_movie_detail")
    }

    private val observeMovies = Observer<MovieResult> {
        adapter.updateMovies(it.results, clearList)
        if(it.results.isNullOrEmpty()) {
            binding.imageViewDoYourSearch.visibility = View.VISIBLE
            binding.textViewDoYourSearch.visibility = View.VISIBLE
            binding.searchRecyclerView.visibility = View.INVISIBLE
        }
        if(clearList) {
            clearList = false
        }
    }

    private val observerItems = Observer<Int> { page ->

        binding.imageViewDoYourSearch.visibility = View.INVISIBLE
        binding.textViewDoYourSearch.visibility = View.INVISIBLE
        binding.searchRecyclerView.visibility = View.VISIBLE

        if (genreId != null && sortBy != null) {
            viewModel.getFilteredMoviesByGenre(page, genreId!!, sortBy!!)
        } else if (searchString.isNotEmpty()){
            viewModel.getFilteredMovies(page, searchString)
        } else {
            binding.imageViewDoYourSearch.visibility = View.VISIBLE
            binding.textViewDoYourSearch.visibility = View.VISIBLE
            binding.searchRecyclerView.visibility = View.INVISIBLE
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        genreId = arguments?.getInt("genre_key")
        sortBy = arguments?.getString("sortBy_key")

        binding = SearchFragmentBinding.bind(view)
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        viewModel.movieResult.observe(viewLifecycleOwner, observeMovies)
        viewModel.page.observe(viewLifecycleOwner, observerItems)

        setupFilter()
        setupEnterKey()
        setupRecyclerView()

        (requireActivity() as? HomeActivity)?.setSelectedItemOnBottomNav(1)
        setEventsForButtons()

    }

    private fun setEventsForButtons(){
        binding.searchRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                        viewModel.nextPage()
                }
            }
        })
    }

    private fun setupFilter() {
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(stringToFilter: CharSequence?, p1: Int, count: Int, p3: Int) {}

            override fun onTextChanged(stringToFilter: CharSequence?, p1: Int, p2: Int, count: Int) {
                stringToFilter?.let { text ->
                    if (text.length > 2) {
                        searchString = text.toString()
                    } else {
                        searchString = ""
                    }
                }
            }

            override fun afterTextChanged(stringToFilter: Editable?) {}

        })
    }

    private fun setupEnterKey() {
        binding.searchEditText.isSingleLine = true
        binding.searchEditText.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {

                genreId = null
                clearList = true
                page = 1

                if(searchString.isNotEmpty()) {
                    viewModel.getFilteredMovies(page, searchString)
                    binding.imageViewDoYourSearch.visibility = View.INVISIBLE
                    binding.textViewDoYourSearch.visibility = View.INVISIBLE
                    binding.searchRecyclerView.visibility = View.VISIBLE
                } else {
                    binding.imageViewDoYourSearch.visibility = View.VISIBLE
                    binding.textViewDoYourSearch.visibility = View.VISIBLE
                    binding.searchRecyclerView.visibility = View.INVISIBLE
                }
                val imm = requireActivity().getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(v.windowToken, 0)
                v.clearFocus()
                binding.searchEditText.isCursorVisible = false
                return@OnKeyListener true
            }
            false
        })
    }

    private fun setupRecyclerView() {

        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.normal_padding)

        binding.searchRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.searchRecyclerView.adapter = adapter
        binding.searchRecyclerView.addItemDecoration(SpacesItemDecoration(spanCount = 3, spacing = spacingInPixels, includeEdge = true))
    }

}