package com.example.moviedb.data.source.remote.network2

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.source.remote.network.Api
import com.example.moviedb.util.rx.BaseScheduler
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.Executor

class PageKeyedMovieDataSource(
    private val api: Api,
    private val scheduler: BaseScheduler,
    private val retryExecutor: Executor
) : PageKeyedDataSource<Int, Movie>() {

    private val compositeDisposable = CompositeDisposable()

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

        val disposable = api.getNowPlaying(1)
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())
            .subscribe({ response ->
                retry = null
                networkState.postValue(NetworkState.LOADED)
                initialLoad.postValue(NetworkState.LOADED)
                callback.onResult(response.mResult ?: emptyList(), null, 2)
            }, {
                val error = NetworkState.error(it.message)
                networkState.postValue(error)
                initialLoad.postValue(error)

                retry = {
                    loadInitial(params, callback)
                }
            })
        compositeDisposable.add(disposable)

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {

        networkState.postValue(NetworkState.LOADING)

        val disposable = api.getNowPlaying(params.key)
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())
            .subscribe({ response ->
                networkState.postValue(NetworkState.LOADED)
                retry = null
                val nextKey = if (params.key == response.totalPage) {
                    null
                } else {
                    params.key + 1
                }
                callback.onResult(response.mResult ?: emptyList(), nextKey)
            }, {
                networkState.postValue(NetworkState.error(it.message))

                retry = {
                    loadAfter(params, callback)
                }
            })
        compositeDisposable.add(disposable)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {

    }

}