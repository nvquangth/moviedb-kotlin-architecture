package com.example.moviedb.data.repository

import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.source.MovieDataSource
import com.example.moviedb.util.scheduler.BaseScheduler
import kotlinx.coroutines.launch

class MovieRepositoryImpl(
    private val scheduler: BaseScheduler,
    private val remote: MovieDataSource.Remote,
    private val local: MovieDataSource.Local
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
        fromServer: Boolean,
        id: Int,
        success: suspend (Movie?) -> Unit,
        fail: suspend (Throwable) -> Unit
    ) {
        scheduler.ioScope.launch {
            try {
                success(
                    if (fromServer) {
                        remote.getMovie(id)
                    } else {
                        local.getMovie(id)
                    }
                )
            } catch (e: Throwable) {
                fail(e)
            }
        }
    }

    override fun getMovies(
        success: suspend (List<Movie>) -> Unit,
        fail: suspend (Throwable) -> Unit
    ) {
        scheduler.ioScope.launch {
            try {
                success(local.getMovies())
            } catch (e: Throwable) {
                fail(e)
            }
        }
    }

    override fun addMovie(
        movie: Movie,
        success: suspend (Unit) -> Unit,
        fail: suspend (Throwable) -> Unit
    ) {
        scheduler.ioScope.launch {
            try {
                success(local.addMovie(movie))
            } catch (e: Throwable) {
                fail(e)
            }
        }
    }

    override fun deleteMovie(
        movie: Movie,
        success: suspend (Unit) -> Unit,
        fail: suspend (Throwable) -> Unit
    ) {
        scheduler.ioScope.launch {
            try {
                success(local.deleteMovie(movie))
            } catch (e: Throwable) {
                fail(e)
            }
        }
    }

    override fun updateMovie(
        movie: Movie,
        success: suspend (Unit) -> Unit,
        fail: suspend (Throwable) -> Unit
    ) {
        scheduler.ioScope.launch {
            try {
                success(local.updateMovie(movie))
            } catch (e: Throwable) {
                fail(e)
            }
        }
    }
}