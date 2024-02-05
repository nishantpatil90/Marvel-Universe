package com.example.marveluniverse.ui.util

import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

@BindingAdapter("imageUrl", "error")
fun ImageView.loadImage(url: String?, error: Drawable) {
    Glide.with(this)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.DATA)
        .error(error)
        .into(this)
}

@BindingAdapter("textIf")
fun TextView.textIf(value: String?) {
    text = value
    isVisible = !value.isNullOrBlank()
}
