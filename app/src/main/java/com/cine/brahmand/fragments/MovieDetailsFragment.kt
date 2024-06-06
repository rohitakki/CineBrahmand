package com.cine.brahmand.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cine.brahmand.R
import com.cine.brahmand.adapter.MovieRailAdapter
import com.cine.brahmand.databinding.FragmentMovieDetailsBinding
import com.cine.brahmand.models.simple.Movie
import com.cine.brahmand.models.simple.MovieDetails
import com.cine.brahmand.utils.NetworkResult
import com.cine.brahmand.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel by activityViewModels<MainViewModel>()

    @Inject
    internal lateinit var similarAdapter: MovieRailAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(layoutInflater, container, false)
        init()
        return binding.root
    }

    private fun init() {
        setupRecyclerView()
        setupObservers()
        fetchDetails()
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun fetchDetails() {
        val id = arguments?.getLong(MOVIE_ID)
        mainViewModel.fetchMovieDetails(id!!)
        mainViewModel.fetchSimilarMovies(id)
    }

    private fun setupObservers() {
        mainViewModel.movieDetailsLiveData.observe(viewLifecycleOwner) {
            when(it) {
                is NetworkResult.Success -> {
                    displayMovieDetails(it.data)
                }
                is NetworkResult.Error -> { showError(it.errorMessage!!) }
                is NetworkResult.Loading -> { }
            }
        }

        mainViewModel.similarMoviesLiveData.observe(viewLifecycleOwner) {
            when(it) {
                is NetworkResult.Error -> { showError(it.errorMessage!!) }
                is NetworkResult.Loading -> {}
                is NetworkResult.Success -> { displaySimilarMovies(it.data!!) }
            }
        }
    }

    private fun displaySimilarMovies(movies: List<Movie>) {
        similarAdapter.submitList(movies)
    }

    private fun displayMovieDetails(details: MovieDetails?) {
        details?.let {
            binding.movieDetails = details
            if (!details.homePageUrl.isNullOrBlank()) {
                binding.playButton.setOnClickListener {
                    navigateToMoviePageInBrowser(details.homePageUrl!!)
                }
            }
        }
    }

    private fun showError(errorMessage: String) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
    }

    private fun setupRecyclerView() {
        binding.similarMoviesRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = similarAdapter
        }
        similarAdapter.setOnClick { id -> showMovieDetails(id) }
    }

    private fun showMovieDetails(id: Long) {
        val bundle = Bundle()
        bundle.putLong(MOVIE_ID, id)
        findNavController().navigate(R.id.action_movieDetailsFragment_self, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigateToMoviePageInBrowser(homePageUrl: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(homePageUrl))
        requireActivity().startActivity(browserIntent)
    }

    companion object {
        const val MOVIE_ID = "MOVIE_ID"
    }
}