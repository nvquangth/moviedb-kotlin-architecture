package com.example.moviedb.di

import com.example.moviedb.data.source.remote.network.createService
import com.example.moviedb.data.source.remote.network.createServiceClient
import com.example.moviedb.ui.home.HomeViewModel
import com.example.moviedb.ui.main.MainViewModel
import org.koin.androidx.viewmodel.experimental.builder.viewModel
import org.koin.dsl.module.module

val viewModelModule = module {
    viewModel<HomeViewModel>()
    viewModel<MainViewModel>()
}

val networkModule = module {
    single { createService() }
    single { createServiceClient() }
}

val appModule = listOf(
    viewModelModule,
    networkModule
)