package com.example.moviedb.di

import com.example.moviedb.data.source.remote.network.createService
import com.example.moviedb.data.source.remote.network.createServiceClient
import org.koin.dsl.module

val networkModule = module {
    single { createService() }
    single { createServiceClient() }
}

val appModule = listOf(
    networkModule
)