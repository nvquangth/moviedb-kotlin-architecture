package com.example.moviedb.data.source.remote.network2

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.source.remote.network.Api
import com.example.moviedb.util.rx.BaseScheduler
import java.util.concurrent.Executor

class MovieDataSourceFactory(
    private val api: Api,
    private val scheduler: BaseScheduler,
    private val executor: Executor
) : DataSource.Factory<Int, Movie>() {

    val sourceLiveData = MutableLiveData<PageKeyedMovieDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val source = PageKeyedMovieDataSource(api, scheduler, executor)
        sourceLiveData.postValue(source)
        return source
    }

}