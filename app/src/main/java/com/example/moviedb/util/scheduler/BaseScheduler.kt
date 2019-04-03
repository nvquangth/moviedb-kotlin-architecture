package com.example.moviedb.util.scheduler

import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

interface BaseScheduler {

    val ioContext: CoroutineContext

    val uiContext: CoroutineContext

    val ioScope: CoroutineScope

    val uiScope: CoroutineScope
}