package com.example.moviedb.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

open class BaseViewHolder<VB: ViewDataBinding> constructor(val binding: VB) :
    RecyclerView.ViewHolder(binding.root)