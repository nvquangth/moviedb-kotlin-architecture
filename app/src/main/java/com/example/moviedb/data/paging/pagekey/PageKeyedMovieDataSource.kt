package com.example.moviedb.data.paging.pagekey

import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.model.MovieResponse
import com.example.moviedb.data.source.remote.network.Api
import com.example.moviedb.util.scheduler.BaseScheduler
import kotlinx.coroutines.Deferred
import java.util.concurrent.Executor

class PageKeyedMovieDataSource(
    private val api: Api,
    scheduler: BaseScheduler,
    executor: Executor
) : PageKeyedDataSource<Movie>(scheduler, executor) {
    override suspend fun getTotalPage(): Int {
        response?.let {
            it.await().apply {
                if (this is MovieResponse) {
                    this.totalPage?.let { totalPage ->
                        return totalPage
                    }
                }
            }
        }
        return 1
    }

    override suspend fun getResult(): List<Movie> {
        response?.let {
            it.await().apply {
                if (this is MovieResponse) {
                    this.mResult?.let { movies ->
                        return movies
                    }
                }
            }
        }
        return emptyList()
    }

    override suspend fun getResponse(params: LoadParams<Int>): Deferred<Any> =
        api.getNowPlaying(params.key)

    override suspend fun getDataFirstPage(): List<Movie> =
        api.getNowPlaying(1).await().mResult ?: emptyList()
}