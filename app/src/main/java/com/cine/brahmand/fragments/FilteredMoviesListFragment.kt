package com.cine.brahmand.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cine.brahmand.databinding.FragmentFilteredMoviesListBinding


class FilteredMoviesListFragment : Fragment() {

    private var _binding: FragmentFilteredMoviesListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilteredMoviesListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}