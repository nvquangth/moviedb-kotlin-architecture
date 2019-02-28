package com.example.moviedb.ui.nowplaying

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviedb.base.BaseViewModel
import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.repository.MovieRepository
import com.example.moviedb.util.rx.BaseScheduler
import io.reactivex.disposables.Disposable

class NowPlayingViewModel(
    private val repository: MovieRepository,
    private val scheduler: BaseScheduler
) :
    BaseViewModel() {

    private val movies: MutableLiveData<MutableList<Movie>> = MutableLiveData()

    fun getMovies(page: Int) {
        val disposable: Disposable = repository.getMovies(page)
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())
            .subscribe({ movie ->
                movies.value = movie.toMutableList()
            }, { throwable ->

            })
        compositeDisposable.add(disposable)
    }

    fun getData(): MutableLiveData<MutableList<Movie>> = movies
}