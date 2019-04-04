package com.example.moviedb.base

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import com.example.moviedb.data.paging.NetworkState
import java.util.concurrent.Executors

abstract class BasePageListAdapter<T>(callback: DiffUtil.ItemCallback<T>) :
    PagedListAdapter<T, BaseViewHolder<ViewDataBinding>>(
        AsyncDifferConfig.Builder<T>(callback)
            .setBackgroundThreadExecutor(Executors.newSingleThreadExecutor())
            .build()
    ) {

    var networkState: NetworkState? = null
        set(value) {
            val previousState = this.networkState
            val hadExtraRow = hasExtraRow()
            field = value
            val hasExtraRow = hasExtraRow()
            if (hadExtraRow != hasExtraRow) {
                if (hadExtraRow) {
                    notifyItemRemoved(super.getItemCount())
                } else {
                    notifyItemInserted(super.getItemCount())
                }
            } else if (hasExtraRow && previousState != value) {
                notifyItemChanged(itemCount - 1)
            }
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<ViewDataBinding> = BaseViewHolder(createBinding(parent, viewType))

    override fun onBindViewHolder(holder: BaseViewHolder<ViewDataBinding>, position: Int) {
        bind(holder.binding, position)
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    protected fun hasExtraRow() = networkState != null && networkState != NetworkState.LOADED

    abstract fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding

    abstract fun bind(binding: ViewDataBinding, position: Int)
}