package com.example.moviedb.data.source.local.sqlite

import androidx.room.*
import com.example.moviedb.data.model.Movie
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface MovieDao {

    @Insert
    fun insert(movie: Movie): Completable

    @Update
    fun update(movie: Movie): Completable

    @Delete
    fun delete(movie: Movie): Completable

    @Query("SELECT * FROM movie")
    fun getMovies(): Single<List<Movie>>

    @Query("SELECT * FROM movie WHERE id = :id")
    fun getMovie(id: Int): Single<Movie>
}