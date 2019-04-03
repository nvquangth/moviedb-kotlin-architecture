package com.example.moviedb.data.paging

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

data class Listing<T> (
    val pageList: LiveData<PagedList<T>>,
    val networkState: LiveData<NetworkState>,
    val refreshState: LiveData<NetworkState>,
    val refresh: () -> Unit,
    val retry: () -> Unit
)