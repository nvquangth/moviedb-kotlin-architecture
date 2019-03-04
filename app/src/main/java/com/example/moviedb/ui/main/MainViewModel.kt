package com.example.moviedb.ui.main

import androidx.lifecycle.MutableLiveData
import com.example.moviedb.base.BaseViewModel

class MainViewModel : BaseViewModel() {
    var actionBarState: MutableLiveData<Boolean> = MutableLiveData(false)
}