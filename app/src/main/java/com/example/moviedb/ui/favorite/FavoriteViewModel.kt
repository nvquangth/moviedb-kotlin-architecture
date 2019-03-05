package com.example.moviedb.ui.favorite

import androidx.lifecycle.MutableLiveData
import com.example.moviedb.base.BaseViewModel
import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.repository.MovieRepository
import com.example.moviedb.util.rx.BaseScheduler
import io.reactivex.disposables.Disposable

class FavoriteViewModel(
    private val scheduler: BaseScheduler,
    private val repository: MovieRepository
) : BaseViewModel() {

    var movies: MutableLiveData<MutableList<Movie>> = MutableLiveData()

    fun getMovies() {
        val disposable: Disposable = repository.getMovies()
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())
            .subscribe({
                movies.value = it.toMutableList()
            }, {

            })
        compositeDisposable.add(disposable)
    }
}