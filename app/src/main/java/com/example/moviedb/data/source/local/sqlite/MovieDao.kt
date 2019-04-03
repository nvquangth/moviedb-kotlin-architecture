package com.example.moviedb.data.source.local.sqlite

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Query
import androidx.room.OnConflictStrategy
import com.example.moviedb.data.model.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Movie)

    @Update
    suspend fun update(movie: Movie)

    @Delete
    suspend fun delete(movie: Movie)

    @Query("SELECT * FROM movie")
    suspend fun getMovies(): List<Movie>

    @Query("SELECT * FROM movie WHERE id = :id")
    suspend fun getMovie(id: Int): Movie?
}