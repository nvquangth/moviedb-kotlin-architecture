package com.example.moviedb.data.source

import androidx.lifecycle.LiveData
import com.example.moviedb.data.model.Movie
import io.reactivex.Completable
import io.reactivex.Single

interface MovieDataSource {

    interface Remote {

        suspend fun getMovies(page: Int = 1): List<Movie>

        suspend fun getMovie(id: Int): Movie
    }

    interface Local {

        fun addMovie(movie: Movie): Completable

        fun deleteMovie(movie: Movie): Completable

        fun getMovies(): LiveData<List<Movie>>

        fun getMovie(id: Int): Single<Movie>
    }
}