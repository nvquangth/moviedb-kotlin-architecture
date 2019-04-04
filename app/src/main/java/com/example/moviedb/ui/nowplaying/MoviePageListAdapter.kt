package com.example.moviedb.ui.nowplaying

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.example.moviedb.R
import com.example.moviedb.base.BasePageListAdapter
import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.paging.Status
import com.example.moviedb.databinding.ItemMovieBinding
import com.example.moviedb.databinding.NetworkStateItemBinding

class MoviePageListAdapter(
    private val listener: (Movie) -> Unit,
    private val retryCallback: () -> Unit
) : BasePageListAdapter<Movie>(object : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.mId == newItem.mId
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}) {

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.network_state_item
        } else {
            R.layout.item_movie
        }
    }

    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        when (viewType) {
            R.layout.item_movie -> {
                val binding: ViewDataBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_movie,
                    parent,
                    false
                )
                binding.root.setOnClickListener {
                    if (binding is ItemMovieBinding) {
                        binding.item?.let {
                            listener.invoke(it)
                        }
                    }
                }
                return binding
            }
            R.layout.network_state_item -> {
                val binding: ViewDataBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.network_state_item,
                    parent,
                    false
                )
                if (binding is NetworkStateItemBinding) {
                    binding.retryButton.setOnClickListener {
                        retryCallback.invoke()
                    }
                }
                return binding
            }
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    override fun bind(binding: ViewDataBinding, position: Int) {
        when (getItemViewType(position)) {
            R.layout.item_movie -> {
                if (binding is ItemMovieBinding) {
                    binding.item = getItem(position)
                }
            }
            R.layout.network_state_item -> {
                if (binding is NetworkStateItemBinding) {
                    binding.isShowProgressBar = networkState?.status == Status.RUNNING
                    binding.isRetry = networkState?.status == Status.FAILED
                    binding.isShowMsg = networkState?.msg != null
                    binding.errorMessage = networkState?.msg
                }
            }
        }
    }
}