package com.example.moviedb.data.source

import com.example.moviedb.data.model.Movie
import io.reactivex.Single

interface MovieDataSource {
    interface Remote {
        fun getMovies(page: Int = 1): Single<List<Movie>>

        fun getMovive(id: Int): Single<Movie>
    }
}