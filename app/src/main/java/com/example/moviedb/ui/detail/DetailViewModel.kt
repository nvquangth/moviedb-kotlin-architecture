package com.example.moviedb.ui.detail

import androidx.databinding.ObservableField
import com.example.moviedb.base.BaseViewModel
import com.example.moviedb.data.model.Movie

class DetailViewModel : BaseViewModel() {
    var movie: ObservableField<Movie> = ObservableField()

    fun setMovie(item: Movie) {
        movie.set(item)
    }
}