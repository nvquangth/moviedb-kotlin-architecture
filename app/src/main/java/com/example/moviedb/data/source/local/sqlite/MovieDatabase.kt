package com.example.moviedb.data.source.local.sqlite

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moviedb.data.model.Movie

@Database(entities = [Movie::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}