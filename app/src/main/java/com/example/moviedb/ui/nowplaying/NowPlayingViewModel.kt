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
    var refreshData: MutableLiveData<Boolean> = MutableLiveData()
    var currentPage = 1
    var tmpPage = 1

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
                currentPage++
                if (refreshData.value == true && tmpPage == currentPage) {
                    refreshData.value = false
                }
            }, { throwable ->
            })
        compositeDisposable.add(disposable)
    }

    fun onRefresh() {
        refreshData.value = true
        tmpPage = currentPage
        currentPage = 1
        movies.value = null
        for (page in 1 until tmpPage) {
            getMovies(page)
        }
    }

    fun getData(): MutableLiveData<MutableList<Movie>> = movies
}