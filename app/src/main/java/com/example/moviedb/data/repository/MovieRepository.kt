package com.example.moviedb.data.repository

import com.example.moviedb.data.model.Movie

interface MovieRepository {

    fun getMovies(
        page: Int,
        success: suspend (List<Movie>) -> Unit,
        fail: suspend (Throwable) -> Unit
    )

    fun getMovie(
        id: Int,
        success: suspend (Movie) -> Unit,
        fail: suspend (Throwable) -> Unit
    )
}