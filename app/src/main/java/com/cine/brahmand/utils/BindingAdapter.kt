package com.cine.brahmand.utils

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("imageFromUrl")
fun AppCompatImageView.imageFromUrl(url: String) {
    Picasso.get().load(url).into(this)
}