package com.example.moviedb.util.scheduler

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class SchedulerProvider : BaseScheduler {

    override val ioContext: CoroutineContext = Job() + Dispatchers.IO

    override val uiContext: CoroutineContext = Job() + Dispatchers.Main

    override val ioScope: CoroutineScope = CoroutineScope(uiContext)

    override val uiScope: CoroutineScope = CoroutineScope(uiContext)
}