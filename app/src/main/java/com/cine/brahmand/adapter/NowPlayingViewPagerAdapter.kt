package com.cine.brahmand.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cine.brahmand.databinding.ItemNowPlayingBinding
import com.cine.brahmand.models.simple.Movie
import javax.inject.Inject

class NowPlayingViewPagerAdapter @Inject constructor(): ListAdapter<Movie, NowPlayingViewPagerAdapter.Holder>(diffCallback) {

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemNowPlayingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) = holder.bind(getItem(position))

    override fun getItemId(position: Int) = getItem(position).id

    inner class Holder(private var binding: ItemNowPlayingBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Movie) {
            binding.movie = item
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(old: Movie, new: Movie) = old.id == new.id
            override fun areContentsTheSame(old: Movie, new: Movie) = old == new
        }
    }
}