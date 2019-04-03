package com.example.moviedb.ui.favorite

import androidx.lifecycle.MutableLiveData
import com.example.moviedb.base.BaseViewModel
import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.repository.MovieRepository
import com.example.moviedb.util.scheduler.BaseScheduler
import kotlinx.coroutines.withContext

class FavoriteViewModel(
    private val scheduler: BaseScheduler,
    private val repository: MovieRepository
) : BaseViewModel() {
    val movies = MutableLiveData<List<Movie>>()

    fun getMovies() {
        repository.getMovies({
            withContext(scheduler.uiContext) {
                movies.value = it
            }
        }, {

        })
    }
}