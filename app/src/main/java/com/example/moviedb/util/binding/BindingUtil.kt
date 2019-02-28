package com.example.moviedb.util.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.moviedb.util.Constant

object BindingUtil {

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun setImage(imageView: ImageView, url: String) {
        Glide.with(imageView)
            .load("${Constant.BASE_IMG_URL}$url")
            .into(imageView)
    }
}