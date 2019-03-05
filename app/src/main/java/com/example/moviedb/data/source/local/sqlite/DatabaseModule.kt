package com.example.moviedb.data.source.local.sqlite

import android.content.Context
import androidx.room.Room

fun createDatabase(context: Context) =
    Room.databaseBuilder(context, MovieDatabase::class.java, "moviedb").build()

fun createDao(movieDatabase: MovieDatabase) = movieDatabase.movieDao()