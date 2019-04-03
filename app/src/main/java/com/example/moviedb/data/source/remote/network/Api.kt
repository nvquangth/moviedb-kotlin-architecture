package com.example.moviedb.data.source.remote.network

import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.model.MovieResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("/3/movie/now_playing")
    fun getNowPlaying(@Query("page") page: Int): Deferred<MovieResponse>

    @GET("/3/movie/{movie_id}")
    fun getMovie(@Path("movie_id") id: Int): Deferred<Movie>
}