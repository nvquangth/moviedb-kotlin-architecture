package com.example.moviedb.data.repository

import com.example.moviedb.data.model.Movie

interface MovieRepository {

    fun getMovies(
        page: Int,
        success: suspend (List<Movie>) -> Unit,
        fail: suspend (Throwable) -> Unit
    )

    fun getMovie(
        fromServer: Boolean,
        id: Int,
        success: suspend (Movie?) -> Unit,
        fail: suspend (Throwable) -> Unit
    )

    fun getMovies(
        success: suspend (List<Movie>) -> Unit,
        fail: suspend (Throwable) -> Unit
    )

    fun addMovie(
        movie: Movie,
        success: suspend (Unit) -> Unit,
        fail: suspend (Throwable) -> Unit
    )

    fun deleteMovie(
        movie: Movie,
        success: suspend (Unit) -> Unit,
        fail: suspend (Throwable) -> Unit
    )

    fun updateMovie(
        movie: Movie,
        success: suspend (Unit) -> Unit,
        fail: suspend (Throwable) -> Unit
    )
}