package com.example.moviedb.util.scheduler

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class SchedulerProvider: BaseScheduler {

    override val ioContext: CoroutineContext = Job() + Dispatchers.IO

    override val uiContext: CoroutineContext = Job() + Dispatchers.Main

    override val ioScope: CoroutineScope = CoroutineScope(uiContext)

    override val uiScope: CoroutineScope = CoroutineScope(uiContext)

    override fun io(): Scheduler = Schedulers.io()

    override fun ui(): Scheduler = AndroidSchedulers.mainThread()
}