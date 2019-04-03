package com.example.moviedb.data.source.local

import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.source.MovieDataSource
import com.example.moviedb.data.source.local.sqlite.MovieDao

class MovieLocalDataSource(private val dao: MovieDao) : MovieDataSource.Local {

    override suspend fun addMovie(movie: Movie) = dao.insert(movie)

    override suspend fun deleteMovie(movie: Movie) = dao.delete(movie)

    override suspend fun updateMovie(movie: Movie) = dao.update(movie)

    override suspend fun getMovies(): List<Movie> = dao.getMovies()

    override suspend fun getMovie(id: Int): Movie? = dao.getMovie(id)
}