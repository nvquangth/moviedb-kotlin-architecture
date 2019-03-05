package com.example.moviedb.ui.nowplaying

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
    var loading: MutableLiveData<Boolean> = MutableLiveData()
    var isSuccess: MutableLiveData<Boolean> = MutableLiveData()

    fun getMovies(page: Int) {
        loading.value = true
        val disposable: Disposable = repository.getMovies(page)
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())
            .doAfterTerminate {
                loading.value = false
            }
            .subscribe({ movies ->
                if (this.movies.value == null) {
                    this.movies.value = movies.toMutableList()
                } else {
                    val data: MutableList<Movie> = this.movies.value!!
                    data.addAll(movies)
                    this.movies.value = data
                }
                isSuccess.value = true
            }, { throwable ->
                isSuccess.value = false
            })
        compositeDisposable.add(disposable)
    }

    fun getData(): MutableLiveData<MutableList<Movie>> = movies
}