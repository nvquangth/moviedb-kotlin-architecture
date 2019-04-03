package com.example.moviedb.data.source

import com.example.moviedb.data.model.Movie

interface MovieDataSource {

    interface Remote {

        suspend fun getMovies(page: Int = 1): List<Movie>

        suspend fun getMovie(id: Int): Movie
    }

    interface Local {

        suspend fun addMovie(movie: Movie)

        suspend fun deleteMovie(movie: Movie)

        suspend fun updateMovie(movie: Movie)

        suspend fun getMovies(): List<Movie>

        suspend fun getMovie(id: Int): Movie?
    }
}