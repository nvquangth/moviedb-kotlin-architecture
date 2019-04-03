package com.example.moviedb.data.source.remote.network2

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.source.remote.network.Api
import com.example.moviedb.util.scheduler.BaseScheduler
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.Executor

class PageKeyedMovieDataSource(
    private val api: Api,
    private val scheduler: BaseScheduler,
    private val retryExecutor: Executor
) : PageKeyedDataSource<Int, Movie>() {

    private var retry: (() -> Any)? = null

    val networkState = MutableLiveData<NetworkState>()
    val initialLoad = MutableLiveData<NetworkState>()

    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.let {
            retryExecutor.execute {
                it.invoke()
            }
        }
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        networkState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)

        scheduler.ioScope.launch {
            try {
                val result = api.getNowPlaying(1).await().mResult ?: emptyList()

                withContext(scheduler.uiContext) {
                    retry = null
                    networkState.postValue(NetworkState.LOADED)
                    initialLoad.postValue(NetworkState.LOADED)
                    callback.onResult(result, null, 2)
                }
            } catch (e: Throwable) {
                val error = NetworkState.error(e.message)
                networkState.postValue(error)
                initialLoad.postValue(error)

                retry = {
                    loadInitial(params, callback)
                }
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        networkState.postValue(NetworkState.LOADING)

        scheduler.ioScope.launch {
            try {
                val response = api.getNowPlaying(params.key)
                val totalPage = response.await().totalPage
                val result = response.await().mResult

                withContext(scheduler.uiContext) {
                    val nextKey = if (params.key == totalPage) {
                        null
                    } else {
                        params.key + 1
                    }
                    networkState.postValue(NetworkState.LOADED)
                    retry = null
                    callback.onResult(result ?: emptyList(), nextKey)
                }
            } catch (e: Throwable) {
                networkState.postValue(NetworkState.error(e.message))

                retry = {
                    loadAfter(params, callback)
                }
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {

    }
}