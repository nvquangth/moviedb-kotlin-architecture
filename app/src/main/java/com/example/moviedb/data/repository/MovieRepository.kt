package com.example.moviedb.data.repository

import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.source.MovieDataSource
import io.reactivex.Completable
import io.reactivex.Single

class MovieRepository(
    private val remote: MovieDataSource.Remote,
    private val local: MovieDataSource.Local
) : MovieDataSource.Remote, MovieDataSource.Local {
    override fun addMovie(movie: Movie): Completable = local.addMovie(movie)

    override fun deleteMovie(movie: Movie): Completable = local.deleteMovie(movie)

    override fun getMovies(): Single<List<Movie>> = local.getMovies()

    override fun getMovie(id: Int): Single<Movie> = local.getMovie(id)

    override fun getMovies(page: Int): Single<List<Movie>> = remote.getMovies(page)

    override fun getMovive(id: Int): Single<Movie> = remote.getMovive(id)
}