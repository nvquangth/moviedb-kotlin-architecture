package com.example.moviedb.data.source

import com.example.moviedb.data.model.Movie
import io.reactivex.Completable
import io.reactivex.Single

interface MovieDataSource {
    interface Remote {
        fun getMovies(page: Int = 1): Single<List<Movie>>

        fun getMovive(id: Int): Single<Movie>
    }

    interface Local {
        fun addMovie(movie: Movie): Completable

        fun deleteMovie(movie: Movie): Completable

        fun getMovies(): Single<List<Movie>>

        fun getMovie(id: Int): Single<Movie>
    }
}