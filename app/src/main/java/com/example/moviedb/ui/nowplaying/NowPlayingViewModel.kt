package com.example.moviedb.ui.nowplaying

import androidx.lifecycle.MutableLiveData
import androidx.room.EmptyResultSetException
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
    var refreshData: MutableLiveData<Boolean> = MutableLiveData(false)
    var currentPage = 1
    var tmpMovies: MutableList<Movie> = mutableListOf()
    var count = 0

    fun getMovies(page: Int) {
        if (refreshData.value == false) {
            loading.value = true
        }
        val disposable: Disposable = repository.getMovies(page)
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())
            .doAfterTerminate {
                loading.value = false
            }
            .subscribe({ movies ->
                checkFavorite(movies)
            }, { throwable ->
            })
        compositeDisposable.add(disposable)
    }

    fun onRefresh() {
        refreshData.value = true
        loading.value = false
        currentPage = 1
        tmpMovies.clear()
        count = 0
        getMovies(currentPage)
    }

    fun getData(): MutableLiveData<MutableList<Movie>> = movies

    private fun checkFavorite(movies: List<Movie>?) {
        movies?.let { values ->
            tmpMovies = values.toMutableList()
            for (movie in values.toMutableList()) {
                val disposable: Disposable = repository.getMovie(movie.mId)
                    .subscribeOn(scheduler.io())
                    .observeOn(scheduler.ui())
                    .subscribe({
                        val index = tmpMovies.indexOf(movie)
                        movie.isFavorite = true
                        tmpMovies[index] = movie
                        count++
                        if (count == values.size) {
                            updateMovies(tmpMovies)
                        }
                    }, { throwable ->
                        if (throwable is EmptyResultSetException) {
                            val index = tmpMovies.indexOf(movie)
                            movie.isFavorite = false
                            tmpMovies[index] = movie
                            count++
                            if (count == values.size) {
                                updateMovies(tmpMovies)
                            }
                        }
                    })
                compositeDisposable.add(disposable)
            }
        }
    }

    private fun updateMovies(result: MutableList<Movie>) {
        if (refreshData.value == true || movies.value == null) {
            movies.value = result
        } else {
            movies.value?.let {
                val data: MutableList<Movie> = it
                data.addAll(result)
                movies.value = data
            }
        }
        currentPage++
        if (refreshData.value == true) {
            refreshData.value = false
        }
        count = 0
    }
}