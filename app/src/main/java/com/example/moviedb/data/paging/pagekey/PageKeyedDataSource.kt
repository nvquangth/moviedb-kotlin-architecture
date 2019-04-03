package com.example.moviedb.data.paging.pagekey

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.moviedb.data.paging.NetworkState
import com.example.moviedb.util.scheduler.BaseScheduler
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.Executor

abstract class PageKeyedDataSource<T>(
    private val scheduler: BaseScheduler,
    private val retryExecutor: Executor
) : PageKeyedDataSource<Int, T>() {

    private var retry: (() -> Any)? = null
    protected var response: Deferred<Any>? = null

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
        callback: LoadInitialCallback<Int, T>
    ) {
        networkState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)

        scheduler.ioScope.launch {
            try {
                val result = getDataFirstPage()

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

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        networkState.postValue(NetworkState.LOADING)

        scheduler.ioScope.launch {
            try {
                response = getResponse(params)
                val totalPage = getTotalPage()
                val result = getResult()

                withContext(scheduler.uiContext) {
                    val nextKey = if (params.key == totalPage) {
                        null
                    } else {
                        params.key + 1
                    }
                    networkState.postValue(NetworkState.LOADED)
                    retry = null
                    callback.onResult(result, nextKey)
                }
            } catch (e: Throwable) {
                networkState.postValue(
                    NetworkState.error(
                        e.message
                    )
                )

                retry = {
                    loadAfter(params, callback)
                }
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {

    }

    abstract suspend fun getTotalPage(): Int

    abstract suspend fun getResult(): List<T>

    abstract suspend fun getResponse(params: LoadParams<Int>): Deferred<Any>

    abstract suspend fun getDataFirstPage(): List<T>
}