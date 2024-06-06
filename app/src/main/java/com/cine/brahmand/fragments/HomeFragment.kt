package com.cine.brahmand.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.cine.brahmand.adapter.MovieRailAdapter
import com.cine.brahmand.adapter.NowPlayingViewPagerAdapter
import com.cine.brahmand.databinding.FragmentHomeBinding
import com.cine.brahmand.models.simple.Movie
import com.cine.brahmand.utils.NetworkResult
import com.cine.brahmand.viewmodels.MainViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    @Inject
    internal lateinit var pagerAdapter: NowPlayingViewPagerAdapter
    @Inject
    internal lateinit var popularAdapter: MovieRailAdapter
    @Inject
    internal lateinit var topRatedAdapter: MovieRailAdapter

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        init()
        return binding.root
    }

    private fun init() {
        setupViewPager()
        setupRecyclerViews()
        setupObservers()
        mainViewModel.fetchNowPlaying()
        mainViewModel.fetchPopularMovies(1)
        mainViewModel.fetchTopRatedMovies(1)
    }

    private fun setupRecyclerViews() {
        binding.popularRail.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularAdapter
        }

        binding.topRatedRail.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = topRatedAdapter
        }
    }

    private fun setupObservers() {
        mainViewModel.nowPlayingLiveData.observe(viewLifecycleOwner) { result ->
            when (result) {
                is NetworkResult.Success -> {
                    displayCarousal(result.data)
                }
                is NetworkResult.Loading -> {
                    showLoading()
                }
                is NetworkResult.Error -> {
                    showError(result.errorMessage)
                }
            }
        }

        mainViewModel.popularMoviesLiveData.observe(viewLifecycleOwner) { result ->
            when (result) {
                is NetworkResult.Success -> {
                    displayPopularMovies(result.data)
                }
                is NetworkResult.Loading -> {
                    showLoading()
                }
                is NetworkResult.Error -> {
                    showError(result.errorMessage)
                }
            }
        }

        mainViewModel.topRatedMoviesLiveData.observe(viewLifecycleOwner) { result ->
            when (result) {
                is NetworkResult.Success -> {
                    displayTopRatedMovies(result.data)
                }
                is NetworkResult.Loading -> {
                    showLoading()
                }
                is NetworkResult.Error -> {
                    showError(result.errorMessage)
                }
            }
        }
    }

    private fun displayPopularMovies(data: List<Movie>?) {
        popularAdapter.submitList(data)
    }

    private fun displayTopRatedMovies(data: List<Movie>?) {
        topRatedAdapter.submitList(data)
    }

    private fun showError(errorMessage: String?) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
    }

    private fun showLoading() {

    }

    private fun displayCarousal(data: List<Movie>?) {
        if (!data.isNullOrEmpty()) {
            pagerAdapter.submitList(data)
        }
    }

    private fun setupViewPager() {
        binding.viewPager.apply {
            adapter = pagerAdapter
        }
        TabLayoutMediator(binding.indicatorTabLayout, binding.viewPager){_,_ -> }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}