package com.example.moviedb.data.repository

import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.source.MovieDataSource
import com.example.moviedb.util.scheduler.BaseScheduler
import kotlinx.coroutines.launch

class MovieRepositoryImpl(
    private val scheduler: BaseScheduler,
    private val remote: MovieDataSource.Remote
) : MovieRepository {
    override fun getMovies(
        page: Int,
        success: suspend (List<Movie>) -> Unit,
        fail: suspend (Throwable) -> Unit
    ) {
        scheduler.ioScope.launch {
            try {
                success(remote.getMovies(page))
            } catch (e: Throwable) {
                fail(e)
            }
        }
    }

    override fun getMovie(
        id: Int,
        success: suspend (Movie) -> Unit,
        fail: suspend (Throwable) -> Unit
    ) {
        scheduler.ioScope.launch {
            try {
                success(remote.getMovie(id))
            } catch (e: Throwable) {
                fail(e)
            }
        }
    }
}