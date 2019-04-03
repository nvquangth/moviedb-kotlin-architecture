package com.example.moviedb.di

import com.example.moviedb.data.repository.MovieRepository
import com.example.moviedb.data.repository.MovieRepositoryImpl
import com.example.moviedb.data.source.MovieDataSource
import com.example.moviedb.data.source.local.MovieLocalDataSource
import com.example.moviedb.data.source.local.sqlite.createDao
import com.example.moviedb.data.source.local.sqlite.createDatabase
import com.example.moviedb.data.source.remote.network.MovieRemoteDataSource
import com.example.moviedb.data.source.remote.network.createService
import com.example.moviedb.data.source.remote.network.createServiceClient
import com.example.moviedb.data.source.remote.network2.MovieByPageKeyedRepository
import com.example.moviedb.data.source.remote.network2.MovieDataSourceFactory
import com.example.moviedb.data.source.remote.network2.PageKeyedMovieDataSource
import com.example.moviedb.ui.detail.DetailViewModel
import com.example.moviedb.ui.favorite.FavoriteViewModel
import com.example.moviedb.ui.home.HomeViewModel
import com.example.moviedb.ui.main.MainViewModel
import com.example.moviedb.ui.nowplaying.NowPlayingViewModel
import com.example.moviedb.util.scheduler.BaseScheduler
import com.example.moviedb.util.scheduler.SchedulerProvider
import org.koin.androidx.viewmodel.experimental.builder.viewModel
import org.koin.dsl.module.module
import org.koin.experimental.builder.create
import org.koin.experimental.builder.singleBy
import java.util.concurrent.Executor
import java.util.concurrent.Executors

val viewModelModule = module {
    viewModel<HomeViewModel>()
    viewModel<MainViewModel>()
    viewModel<DetailViewModel>()
    viewModel<FavoriteViewModel>()
    viewModel<NowPlayingViewModel>()
}

val networkModule2 = module {
    single { createExecutor() }
    single { create<MovieByPageKeyedRepository>() }
    single { create<PageKeyedMovieDataSource>() }
    single { create<MovieDataSourceFactory>() }
}

val networkModule = module {
    single { createService() }
    single { createServiceClient() }
}

val repositoryModule = module {
    singleBy<MovieDataSource.Remote, MovieRemoteDataSource>()
    singleBy<MovieDataSource.Local, MovieLocalDataSource>()
    singleBy<MovieRepository, MovieRepositoryImpl>()
}

val rxModule = module {
    singleBy<BaseScheduler, SchedulerProvider>()
}

val databaseModule = module {
    single { createDao(get()) }
    single { createDatabase(get()) }
}

val appModule = listOf(
    viewModelModule,
    networkModule,
    repositoryModule,
    rxModule,
    databaseModule,
    networkModule2
)

fun createExecutor(): Executor = Executors.newFixedThreadPool(5)