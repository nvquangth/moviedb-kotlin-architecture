package com.example.moviedb.ui

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

abstract class EndlessScrollListener(private val layoutManager: RecyclerView.LayoutManager) :
    RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (layoutManager is LinearLayoutManager) {
            if (layoutManager.findLastVisibleItemPosition() == layoutManager.itemCount - 1) {
                loadMore()
            }
        } else if (layoutManager is GridLayoutManager) {
            if (layoutManager.findLastVisibleItemPosition() == layoutManager.itemCount - 1) {
                loadMore()
            }
        } else if (layoutManager is StaggeredGridLayoutManager) {
            if (layoutManager.findLastVisibleItemPositions(IntArray(layoutManager.spanCount))[0] ==
                layoutManager.itemCount - 1
            ) {
                loadMore()
            }
        }
    }

    abstract fun loadMore()
}