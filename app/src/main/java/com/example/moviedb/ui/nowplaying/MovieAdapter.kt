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

class MovieAdapter(private val listener: (Movie) -> Unit) :
    BaseRecyclerAdapter<Movie>(object : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.mId == newItem.mId
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

    }) {
    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        val binding: ViewDataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_movie,
            parent,
            false
        )
        binding.root.setOnClickListener {
            if (binding is ItemMovieBinding) {
                binding.item?.let { movie ->
                    listener.invoke(movie)
                }
            }
        }
        return binding
    }

    override fun bind(binding: ViewDataBinding, item: Movie) {
        if (binding is ItemMovieBinding) binding.item = item
    }
}