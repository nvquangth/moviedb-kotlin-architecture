package com.example.moviedb.data.source.remote.network

import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.source.MovieDataSource
import io.reactivex.Single

class MovieRemoteDataSource(val api: Api) : MovieDataSource.Remote {

    override fun getMovies(page: Int): Single<List<Movie>> {
        return api.getNowPlaying(page).map { response ->
            response.mResult
        }
    }

    override fun getMovive(id: Int): Single<Movie> {
        return api.getMovie(id)
    }
}