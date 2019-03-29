package com.example.moviedb.ui.nowplaying2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviedb.R
import com.example.moviedb.data.model.Movie

class MovieItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val title: TextView = view.findViewById(R.id.text_title)
    private val image: ImageView = view.findViewById(R.id.image)

    fun bind(movie: Movie?) {
        title.text = movie?.mTitle
        Glide.with(image.context)
            .load(movie?.mPosterPath)
            .into(image)
    }

    companion object {

        fun create(parent: ViewGroup): MovieItemViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_movie_2, parent, false)
            return MovieItemViewHolder(view)
        }
    }
}