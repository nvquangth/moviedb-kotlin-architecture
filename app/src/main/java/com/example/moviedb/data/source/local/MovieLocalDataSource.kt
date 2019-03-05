package com.example.moviedb.data.source.local

import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.source.MovieDataSource
import com.example.moviedb.data.source.local.sqlite.MovieDao
import io.reactivex.Completable
import io.reactivex.Single

class MovieLocalDataSource(private val dao: MovieDao): MovieDataSource.Local {
    override fun addMovie(movie: Movie): Completable = dao.insert(movie)

    override fun deleteMovie(movie: Movie): Completable = dao.delete(movie)

    override fun getMovies(): Single<List<Movie>> = dao.getMovies()

    override fun getMovie(id: Int): Single<Movie> = dao.getMovie(id)
}