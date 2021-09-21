package com.example.movieapp.view

import android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.movieapp.R
import com.example.movieapp.databinding.MovieDetailFragmentBinding
import com.example.movieapp.model.Movie
import com.example.movieapp.model.MovieTrailerResult
import com.example.movieapp.utils.checkInternet
import com.example.movieapp.view_model.MovieDetailViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment() : BottomSheetDialogFragment() {

    companion object {
        fun newInstance(id: Long): MovieDetailFragment{
            return MovieDetailFragment().apply {
                val args = Bundle()
                args.putLong("movie_id", id)
                this.arguments = args
            }
        }
    }

    private lateinit var viewModel: MovieDetailViewModel
    private lateinit var binding: MovieDetailFragmentBinding
    private var selectedMovie: Movie? = null
    private var movieId: Long? = 0

    private val movieObserver = Observer<Movie> { result ->

        selectedMovie = result

        Glide.with(requireView())
            .load("https://image.tmdb.org/t/p/w342${result.poster_path}")
            .transform(CenterCrop())
            .into(binding.movieDetailImageView)

        binding.movieDetailGenreTextView.text = result.genres?.get(0)?.name
        binding.movieDetailAverageVoteTextView.text = result.vote_average.toString()
        binding.movieDetailTitleTextView.text = result.title
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.movieDetailOverviewTextView.justificationMode = JUSTIFICATION_MODE_INTER_WORD
        }
        binding.movieDetailOverviewTextView.text = result.overview
    }

    private val movieTrailerObserver = Observer<MovieTrailerResult> {

        if(!it.results.isNullOrEmpty()) {
            binding.movieDetailImageView.visibility = View.INVISIBLE
            binding.movieDetailOfflineImageView.visibility = View.INVISIBLE
            binding.movieDetailVideoView.visibility = View.VISIBLE
            binding.movieDetailVideoView.addYouTubePlayerListener(object :
                AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    super.onReady(youTubePlayer)
                    youTubePlayer.cueVideo(it.results[0].key, 0F)
                }
            })
        } else {
            binding.movieDetailImageView.visibility = View.VISIBLE
            binding.movieDetailOfflineImageView.visibility = View.INVISIBLE
            binding.movieDetailVideoView.visibility = View.INVISIBLE
        }
    }

    private val favoritesMoviesObserver = Observer<List<Movie>> { movies ->
        binding.movieDetailFavoriteCheckbox.isChecked = movies.contains(selectedMovie)
    }

    private val offlineMovieObserver = Observer<Movie> { result ->
        binding.movieDetailOfflineImageView.visibility = View.VISIBLE
        binding.movieDetailImageView.visibility = View.INVISIBLE
        binding.movieDetailVideoView.visibility = View.INVISIBLE

        binding.movieDetailFavoriteCheckbox.isChecked = true

        selectedMovie = result

        binding.movieDetailGenreTextView.text = result.genres?.get(0)?.name
        binding.movieDetailAverageVoteTextView.text = result.vote_average.toString()
        binding.movieDetailTitleTextView.text = result.title
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.movieDetailOverviewTextView.justificationMode = JUSTIFICATION_MODE_INTER_WORD
        }
        binding.movieDetailOverviewTextView.text = result.overview
    }

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.movie_detail_fragment, container, false)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MovieDetailViewModel::class.java)
        binding = MovieDetailFragmentBinding.bind(view)
        viewModel.movieDetail.observe(viewLifecycleOwner, movieObserver)
        viewModel.movieTrailerDetail.observe(viewLifecycleOwner, movieTrailerObserver)
        viewModel.favMovies.observe(viewLifecycleOwner, favoritesMoviesObserver)
        viewModel.offlineMovieDetail.observe(viewLifecycleOwner, offlineMovieObserver)

        movieId = arguments?.getLong("movie_id")
        if(checkInternet(requireContext())) {
            fetchOnlineMovies()
        } else {
            fetchOfflineMovies()
        }


    }
    fun fetchOnlineMovies() {
        if (movieId != null) {
            viewModel.getMovieById(movieId!!)
        }

        viewModel.fetchFavoriteMovies()

        binding.movieDetailFavoriteCheckbox.setOnCheckedChangeListener { buttonView, isChecked ->
            if(selectedMovie != null) {
                if (isChecked) {
                    viewModel.addFavorite(selectedMovie!!.id)
                } else {
                    viewModel.removeFavorite(selectedMovie!!.id)
                }
            }
        }
    }

    fun fetchOfflineMovies() {
        if (movieId != null) {
            viewModel.fetchLocalFavs(movieId!!)
        }
    }
}