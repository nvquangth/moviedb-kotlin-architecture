package com.example.moviedb.ui.nowplaying

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.example.moviedb.R
import com.example.moviedb.base.BaseRecyclerAdapter
import com.example.moviedb.data.model.Movie
import com.example.moviedb.databinding.ItemMovieBinding

class MovieAdapter : BaseRecyclerAdapter<Movie>(object : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.mId == newItem.mId
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }

}) {
    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_movie,
            parent,
            false
        )
    }

    override fun bind(binding: ViewDataBinding, item: Movie) {
        (binding as ItemMovieBinding).item = item
    }
}