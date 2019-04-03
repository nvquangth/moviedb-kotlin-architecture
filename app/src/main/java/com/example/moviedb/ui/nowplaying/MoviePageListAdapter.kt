package com.example.moviedb.ui.nowplaying

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedb.R
import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.paging.NetworkState
import java.lang.IllegalArgumentException

class MoviePageListAdapter(private val listener: (Movie) -> Unit, private val retryCallback: () -> Unit) :
    PagedListAdapter<Movie, RecyclerView.ViewHolder>(object : DiffUtil.ItemCallback<Movie>() {
        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
            oldItem == newItem

        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
            oldItem.mId == newItem.mId
    }) {

    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_movie_2 -> MovieItemViewHolder.create(parent, listener)
            R.layout.network_state_item -> NetworkStateItemViewHolder.create(parent, retryCallback)
            else ->throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)) {
            R.layout.item_movie_2 -> (holder as MovieItemViewHolder).bind(getItem(position))
            R.layout.network_state_item -> (holder as NetworkStateItemViewHolder).binTo(networkState)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.network_state_item
        } else {
            R.layout.item_movie_2
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.LOADED

    fun setNetworkState(newNetworkState: NetworkState?) {

        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }
}