package com.example.moviedb.data.repository

import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.source.MovieDataSource
import io.reactivex.Single

class MovieRepository(private val remote: MovieDataSource.Remote) : MovieDataSource.Remote {
    override fun getMovies(page: Int): Single<List<Movie>> = remote.getMovies(page)

    override fun getMovive(id: Int): Single<Movie> = remote.getMovive(id)
}