package com.example.moviedb.data.source.local.sqlite

import android.content.Context
import androidx.room.Room
import com.example.moviedb.util.Constant

fun createDatabase(context: Context) =
    Room.databaseBuilder(context, MovieDatabase::class.java, Constant.DATABASE_NAME).build()

fun createDao(movieDatabase: MovieDatabase) = movieDatabase.movieDao()