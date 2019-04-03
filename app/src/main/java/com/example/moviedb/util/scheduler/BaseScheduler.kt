package com.example.moviedb.util.scheduler
import io.reactivex.Scheduler
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

interface BaseScheduler {

    fun io(): Scheduler

    fun ui(): Scheduler

    val ioContext: CoroutineContext

    val uiContext: CoroutineContext

    val ioScope: CoroutineScope

    val uiScope: CoroutineScope
}