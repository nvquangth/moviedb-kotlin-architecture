package com.example.moviedb.data.source.remote.network

import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.source.MovieDataSource

class MovieRemoteDataSource(val api: Api) : MovieDataSource.Remote {

    override suspend fun getMovies(page: Int): List<Movie> =
        api.getNowPlaying(page).await().mResult ?: listOf()

    override suspend fun getMovie(id: Int): Movie = api.getMovie(id).await()
}