package com.example.moviedb.base

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import java.util.concurrent.Executors

abstract class BaseRecyclerAdapter<T>(callback: DiffUtil.ItemCallback<T>) :
    ListAdapter<T, BaseViewHolder<ViewDataBinding>>(
    AsyncDifferConfig.Builder<T>(callback)
        .setBackgroundThreadExecutor(Executors.newSingleThreadExecutor())
        .build()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ViewDataBinding> =
        BaseViewHolder(createBinding(parent, viewType))

    override fun onBindViewHolder(holder: BaseViewHolder<ViewDataBinding>, position: Int) {
        bind(holder.binding, getItem(position))
        holder.binding.executePendingBindings()
    }

    override fun submitList(list: MutableList<T>?) {
        val newList = ArrayList<T>()
        if (list != null) {
            newList.addAll(list)
        }
        super.submitList(list)
    }

    abstract fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding

    abstract fun bind(binding: ViewDataBinding, item: T)

}