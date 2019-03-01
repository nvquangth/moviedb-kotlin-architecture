package com.example.moviedb.ui.main

import androidx.lifecycle.MutableLiveData
import com.example.moviedb.base.BaseViewModel

class MainViewModel : BaseViewModel() {
    private val actionBarState: MutableLiveData<Boolean> = MutableLiveData(false)

    fun activeActionbar(isActive: Boolean) {
        actionBarState.value = isActive
    }

    fun getStateActionBar(): MutableLiveData<Boolean> = actionBarState
}