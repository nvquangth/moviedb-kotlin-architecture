package com.example.moviedb.di

import com.example.moviedb.data.repository.MovieRepository
import com.example.moviedb.data.source.MovieDataSource
import com.example.moviedb.data.source.remote.network.MovieRemoteDataSource
import com.example.moviedb.data.source.remote.network.createService
import com.example.moviedb.data.source.remote.network.createServiceClient
import com.example.moviedb.ui.home.HomeViewModel
import com.example.moviedb.ui.main.MainViewModel
import com.example.moviedb.ui.nowplaying.NowPlayingViewModel
import com.example.moviedb.util.rx.BaseScheduler
import com.example.moviedb.util.rx.SchedulerProvider
import org.koin.androidx.viewmodel.experimental.builder.viewModel
import org.koin.dsl.module.module

val viewModelModule = module {
    viewModel<HomeViewModel>()
    viewModel<MainViewModel>()
    viewModel<NowPlayingViewModel>()
}

val networkModule = module {
    single { createService() }
    single { createServiceClient() }
}

val repositoryModule = module {
    single { MovieRepository(get()) }
    single<MovieDataSource.Remote> { MovieRemoteDataSource(get()) }
}

val rxModule = module {
    single<BaseScheduler> { SchedulerProvider() }
}

val appModule = listOf(
    viewModelModule,
    networkModule,
    repositoryModule,
    rxModule
)