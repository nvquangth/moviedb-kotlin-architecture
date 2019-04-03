package com.example.moviedb.ui.nowplaying

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.switchMap
import com.example.moviedb.base.BaseViewModel
import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.paging.Listing
import com.example.moviedb.data.paging.repository.MovieByPageKeyedRepository

class NowPlayingViewModel(private val repository: MovieByPageKeyedRepository) : BaseViewModel() {

    private val repoResult = MutableLiveData<Listing<Movie>>(repository.getData(1))

    val movies = switchMap(repoResult) {
        it.pageList
    }
    val networkState = switchMap(repoResult) {
        it.networkState
    }
    val refreshState = switchMap(repoResult) {
        it.refreshState
    }

    fun refresh() {
        repoResult.value?.refresh?.invoke()
    }

    fun retry() {
        val listing = repoResult.value
        listing?.retry?.invoke()
    }
}