package com.example.moviedb.ui.nowplaying

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviedb.R
import com.example.moviedb.data.model.Movie

class MovieItemViewHolder(view: View, listener: (Movie) -> Unit) : RecyclerView.ViewHolder(view) {

    private val title: TextView = view.findViewById(R.id.text_title)
    private val image: ImageView = view.findViewById(R.id.image)
    private var movie: Movie? = null

    init {
        view.setOnClickListener {
            movie?.apply {
                listener.invoke(this)
            }
        }
    }

    fun bind(movie: Movie?) {
        this.movie = movie
        title.text = movie?.mTitle
        Glide.with(image.context)
            .load(movie?.mPosterPath)
            .into(image)
    }

    companion object {

        fun create(parent: ViewGroup, listener: (Movie) -> Unit): MovieItemViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_movie_2, parent, false)
            return MovieItemViewHolder(view, listener)
        }
    }
}