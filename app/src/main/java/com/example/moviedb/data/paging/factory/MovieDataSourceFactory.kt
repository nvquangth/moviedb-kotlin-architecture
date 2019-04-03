package com.example.moviedb.data.paging.factory

import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.paging.pagekey.PageKeyedDataSource
import com.example.moviedb.data.paging.pagekey.PageKeyedMovieDataSource
import com.example.moviedb.data.source.remote.network.Api
import com.example.moviedb.util.scheduler.BaseScheduler
import java.util.concurrent.Executor

class MovieDataSourceFactory(
    private val api: Api,
    private val scheduler: BaseScheduler,
    private val executor: Executor
) : DataSourceFactory<Movie>() {

    override fun createPageKeyedDataSource(): PageKeyedDataSource<Movie> =
        PageKeyedMovieDataSource(api, scheduler, executor)
}