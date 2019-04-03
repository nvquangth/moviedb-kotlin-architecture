package com.example.moviedb.data.paging.repository

import androidx.lifecycle.Transformations
import androidx.paging.toLiveData
import com.example.moviedb.data.paging.Listing
import com.example.moviedb.data.paging.factory.DataSourceFactory
import java.util.concurrent.Executor

abstract class PageKeyedRepository<T>(
    private val executor: Executor
) {

    fun getData(pageSize: Int): Listing<T> {
        val sourceFactory = createDataSourceFactory()
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

    abstract fun createDataSourceFactory(): DataSourceFactory<T>
}