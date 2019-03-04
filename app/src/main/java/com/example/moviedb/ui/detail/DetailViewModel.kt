package com.example.moviedb.ui.detail

import androidx.lifecycle.MutableLiveData
import com.example.moviedb.base.BaseViewModel
import com.example.moviedb.data.model.Movie

class DetailViewModel : BaseViewModel() {
    var movie: MutableLiveData<Movie> = MutableLiveData()
}