package com.example.moviedb.ui.detail

import androidx.lifecycle.MutableLiveData
import com.example.moviedb.base.BaseViewModel
import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.repository.MovieRepository
import com.example.moviedb.util.scheduler.BaseScheduler
import kotlinx.coroutines.withContext

class DetailViewModel(
    private val scheduler: BaseScheduler,
    private val repository: MovieRepository
) : BaseViewModel() {
    var movie: MutableLiveData<Movie> = MutableLiveData()
    var favorite: MutableLiveData<Boolean> = MutableLiveData()

    fun checkFavorite() {
        movie.value?.let {
            repository.getMovie(false, it.mId, {
                withContext(scheduler.uiContext) {
                    favorite.value = it != null
                }
            }, {

            })
        }
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
        repository.deleteMovie(movie, {
            withContext(scheduler.uiContext) {
                favorite.value = false
            }
        }, {

        })
    }

    private fun addMovie(movie: Movie) {
        repository.addMovie(movie, {
            favorite.value = true
        }, {

        })
    }
}