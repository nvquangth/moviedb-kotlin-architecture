package com.example.moviedb.data.source.remote.network2

import androidx.lifecycle.Transformations
import androidx.paging.toLiveData
import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.source.remote.network.Api
import com.example.moviedb.util.rx.BaseScheduler
import java.util.concurrent.Executor

class MovieByPageKeyedRepository(
    private val api: Api,
    private val scheduler: BaseScheduler,
    private val executor: Executor
) {

    fun getNowPlaying(pageSize: Int): Listing<Movie> {
        val sourceFactory = MovieDataSourceFactory(api, scheduler, executor)
        val livePagedList = sourceFactory.toLiveData(pageSize = pageSize, fetchExecutor = executor)
        val refreshState = Transformations.switchMap(sourceFactory.sourceLiveData) {
            it.initialLoad
        }
        return Listing(
            pageList = livePagedList,
            networkState = Transformations.switchMap(sourceFactory.sourceLiveData) {
                it.networkState
            },
            retry = {
                sourceFactory.sourceLiveData.value?.retryAllFailed()
            },
            refresh = {
                sourceFactory.sourceLiveData.value?.invalidate()
            },
            refreshState = refreshState
        )
    }
}