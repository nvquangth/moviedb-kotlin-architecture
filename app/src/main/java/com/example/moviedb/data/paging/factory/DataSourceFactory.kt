package com.example.moviedb.data.paging.factory

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.moviedb.data.paging.pagekey.PageKeyedDataSource

abstract class DataSourceFactory<T> : DataSource.Factory<Int, T>() {

    val sourceLiveData = MutableLiveData<PageKeyedDataSource<T>>()

    override fun create(): DataSource<Int, T> {
        val source = createPageKeyedDataSource()
        sourceLiveData.postValue(source)
        return source
    }

    abstract fun createPageKeyedDataSource(): PageKeyedDataSource<T>
}