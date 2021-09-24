package com.example.movieapp.view

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapp.R
import com.example.movieapp.adapter.FavoritesAdapter
import com.example.movieapp.databinding.OfflineFragmentBinding
import com.example.movieapp.model.Movie
import com.example.movieapp.model.User
import com.example.movieapp.view_model.OfflineViewModel
import dagger.hilt.android.AndroidEntryPoint
import android.widget.AutoCompleteTextView

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Editable
import android.text.TextWatcher
import android.view.Window
import android.widget.Button


@AndroidEntryPoint
class OfflineFragment : Fragment(R.layout.offline_fragment) {

    companion object {
        fun newInstance() = OfflineFragment()
    }

    private lateinit var viewModel: OfflineViewModel
    private lateinit var binding: OfflineFragmentBinding
    private var selectedUser: User? = null
    private var listOfUsers: List<User> = emptyList()
    private var selectedUserToDelete: User? = null
    private var searchTitle: String = ""
    private var adapter = FavoritesAdapter(true){ descriptionClick, removeClick ->
        if(descriptionClick != null) {
            MovieDetailFragment.newInstance(descriptionClick.id).show(parentFragmentManager, "dialog_movie_detail")
        }
    }

    private val observerMovies = Observer<List<Movie>> {
        adapter.updateMovies(it)
    }

    private val observerUsers = Observer<List<User>> {
        listOfUsers = it
        setupArrayAdapter(it)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = OfflineFragmentBinding.bind(view)
        viewModel = ViewModelProvider(this).get(OfflineViewModel::class.java)

        viewModel.movies.observe(viewLifecycleOwner, observerMovies)
        viewModel.users.observe(viewLifecycleOwner, observerUsers)

        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerView.adapter = adapter

        viewModel.fetchUsers()

        setupAutoComplete()
        setupButtonsEvents()

    }

    private fun setupAutoComplete() {

        binding.autoCompleteUsersEmail.isSingleLine = true
        binding.autoCompleteUsersEmail.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {

                searchTitle = binding.textViewTitleOffline.text.toString()

                if(selectedUser != null) {
                    viewModel.fetchFavorites(selectedUser!!.userEmail, searchTitle)
                }
                val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(v.windowToken, 0)
                v.clearFocus()
                binding.autoCompleteUsersEmail.isCursorVisible = false
                return@OnKeyListener true
            }
            false
        })
    }

    private fun setupButtonsEvents() {
        binding.materialButtonSearch.setOnClickListener {
            searchTitle = binding.searchEditText.text.toString()
            if(selectedUser != null) {
                viewModel.fetchFavorites(selectedUser!!.userEmail, searchTitle)
            }
        }
        binding.materialButtonEditUsers.setOnClickListener {
            showDialog()
        }
    }

    private fun showDialog() {
        val dialog = Dialog(requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.edit_local_emails)
        dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val deleteButton = dialog.findViewById(R.id.buttonConfirmDelete) as Button
        val cancelButton = dialog.findViewById(R.id.buttonCancel) as Button

        val arr = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            listOfUsers
        )
        dialog.findViewById<AutoCompleteTextView>(R.id.autoCompleteUsersEmail).setAdapter(arr)
        dialog.findViewById<AutoCompleteTextView>(R.id.autoCompleteUsersEmail).setOnItemClickListener { adapterView, view, i, l ->
            selectedUserToDelete = adapterView.getItemAtPosition(i) as User
        }

        dialog.findViewById<AutoCompleteTextView>(R.id.autoCompleteUsersEmail).addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(stringToFilter: CharSequence?, p1: Int, count: Int, p3: Int) {}

            override fun onTextChanged(stringToFilter: CharSequence?, p1: Int, p2: Int, count: Int) {
                stringToFilter?.let { text ->
                    listOfUsers.forEach {
                        if(text.toString() == it.userEmail) {
                            deleteButton.isEnabled = true
                            return@let
                        }
                    }
                    deleteButton.isEnabled = false
                }
            }

            override fun afterTextChanged(stringToFilter: Editable?) {}

        })

        deleteButton.setOnClickListener {
                viewModel.deleteLocalUser(selectedUserToDelete!!)
                viewModel.fetchUsers()
                dialog.dismiss()
        }
        cancelButton.setOnClickListener { dialog.dismiss() }
        dialog.show()

    }

    private fun setupArrayAdapter(listOfUsers: List<User>) {
        val arr = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            listOfUsers
        )
        binding.autoCompleteUsersEmail.setAdapter(arr)
        binding.autoCompleteUsersEmail.setOnItemClickListener { adapterView, view, i, l ->
            selectedUser = adapterView.getItemAtPosition(i) as User
        }
    }

}