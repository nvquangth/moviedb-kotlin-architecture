package com.example.moviedb.data.paging.repository

import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.paging.factory.DataSourceFactory
import com.example.moviedb.data.paging.factory.MovieDataSourceFactory
import com.example.moviedb.data.source.remote.network.Api
import com.example.moviedb.util.scheduler.BaseScheduler
import java.util.concurrent.Executor

class MovieByPageKeyedRepository(
    private val api: Api,
    private val scheduler: BaseScheduler,
    private val executor: Executor
) : PageKeyedRepository<Movie>(executor) {

    override fun createDataSourceFactory(): DataSourceFactory<Movie> =
        MovieDataSourceFactory(api, scheduler, executor)
}