package com.example.moviedb.ui.nowplaying

import com.example.moviedb.base.BaseViewModel
import com.example.moviedb.data.repository.MovieRepository
import com.example.moviedb.util.rx.BaseScheduler

class NowPlayingViewModel(val repository: MovieRepository, val scheduler: BaseScheduler) :
    BaseViewModel() {
}