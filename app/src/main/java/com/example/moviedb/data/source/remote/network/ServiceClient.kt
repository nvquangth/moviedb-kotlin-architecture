package com.example.moviedb.data.source.remote.network

import com.example.moviedb.util.Constant
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

fun createService(): Retrofit {
    val okHttpBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
    okHttpBuilder.addInterceptor {
        var request = it.request()
        val url =
            request.url().newBuilder().addQueryParameter(Constant.API_KEY_PAR, Constant.API_KEY)
                .build()
        request = request.newBuilder().url(url).build()
        it.proceed(request)
    }
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY
    okHttpBuilder.addInterceptor(logging)

    okHttpBuilder.readTimeout(Constant.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
    okHttpBuilder.connectTimeout(Constant.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
    return Retrofit.Builder().baseUrl(Constant.END_POINT)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .client(okHttpBuilder.build())
        .build()
}

fun createServiceClient(): Api = createService().create(Api::class.java)