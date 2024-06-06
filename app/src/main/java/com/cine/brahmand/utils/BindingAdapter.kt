package com.cine.brahmand.utils

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.cine.brahmand.R
import com.squareup.picasso.Picasso

@BindingAdapter("imageFromUrl")
fun AppCompatImageView.imageFromUrl(url: String) {
    Picasso.get().load(url)
        .placeholder(R.color.default_dot)
        .into(this)
}

@BindingAdapter("carouselImageFromUrl")
fun AppCompatImageView.carouselImageFromUrl(url: String) {
    Picasso.get().load(url)
        .into(this)
}