package com.example.moviedb.ui.nowplaying

import androidx.databinding.ObservableBoolean
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
    val loading: ObservableBoolean = ObservableBoolean(true)

    fun getMovies(page: Int) {
        val disposable: Disposable = repository.getMovies(page)
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())
            .subscribe({ movie ->
                loading.set(false)
                movies.value = movie.toMutableList()
            }, { throwable ->
                loading.set(false)
            })
        compositeDisposable.add(disposable)
    }

    fun getData(): MutableLiveData<MutableList<Movie>> = movies
}