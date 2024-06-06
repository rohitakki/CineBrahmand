package com.cine.brahmand.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cine.brahmand.databinding.ItemMovieRailsBinding
import com.cine.brahmand.models.simple.Movie
import com.cine.brahmand.models.simple.Position
import javax.inject.Inject

class MovieRailAdapter @Inject constructor(): ListAdapter<Movie, MovieRailAdapter.Holder>(diffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemMovieRailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) = holder.bind(getItem(position))

    override fun getItemId(position: Int): Long = getItem(position).id

    inner class Holder(private val binding: ItemMovieRailsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Movie) {
            if (item.position == Position.START) {
                binding.startGap.visibility = View.VISIBLE
                binding.endGap.visibility = View.VISIBLE
            } else {
                binding.startGap.visibility = View.GONE
                binding.endGap.visibility = View.VISIBLE
            }
            binding.movie = item
        }
    }

    companion object {
        private val diffUtilCallback = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }
}