package com.example.moviedb.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.room.EmptyResultSetException
import com.example.moviedb.base.BaseViewModel
import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.repository.MovieRepository
import com.example.moviedb.util.scheduler.BaseScheduler
import io.reactivex.disposables.Disposable

class DetailViewModel(
    private val scheduler: BaseScheduler,
    private val repository: MovieRepository
) : BaseViewModel() {
    var movie: MutableLiveData<Movie> = MutableLiveData()
    var favorite: MutableLiveData<Boolean> = MutableLiveData()

    fun checkFavorite() {
//        movie.value?.let {
//            val disposable: Disposable = repository.getMovie(it.mId)
//                .subscribeOn(scheduler.io())
//                .observeOn(scheduler.ui())
//                .subscribe({
//                    favorite.value = true
//                }, { throwable ->
//                    if (throwable is EmptyResultSetException) {
//                        favorite.value = false
//                    }
//                })
//            compositeDisposable.add(disposable)
//        }
    }

    fun addOrRemoveFavorite() {
        if (favorite.value == true) {
            movie.value?.let {
                deleteMovie(it)
            }
        } else {
            movie.value?.let {
                addMovie(it)
            }
        }
    }

    private fun deleteMovie(movie: Movie) {
//        val disposable: Disposable = repository.deleteMovie(movie)
//            .subscribeOn(scheduler.io())
//            .observeOn(scheduler.ui())
//            .subscribe {
//                favorite.value = false
//            }
//        compositeDisposable.add(disposable)
    }

    private fun addMovie(movie: Movie) {
//        val disposable: Disposable = repository.addMovie(movie)
//            .subscribeOn(scheduler.io())
//            .observeOn(scheduler.ui())
//            .subscribe {
//                favorite.value = true
//            }
//        compositeDisposable.add(disposable)
    }
}