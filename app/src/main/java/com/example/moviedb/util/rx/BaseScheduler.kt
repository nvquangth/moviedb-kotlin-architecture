package com.example.moviedb.util.rx
import io.reactivex.Scheduler

interface BaseScheduler {

    fun io(): Scheduler

    fun ui(): Scheduler
}