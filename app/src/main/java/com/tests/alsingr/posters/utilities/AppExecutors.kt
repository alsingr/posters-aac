package com.tests.alsingr.posters.utilities


import android.os.Handler
import android.os.Looper

import java.util.concurrent.Executor
import java.util.concurrent.Executors

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Global executor pools for the whole application.
 *
 * Grouping tasks like this avoids the effects of task starvation (e.g. disk reads don't wait behind
 * webservice requests).
 *
 * From GoogleSamples
 */
@Singleton
open class AppExecutors(
    private val diskIO: Executor,
    private val networkIO: Executor,
    private val mainThread: Executor
) {

    @Inject
    constructor() : this(
        Executors.newSingleThreadExecutor(),
        Executors.newFixedThreadPool(3),
        MainThreadExecutor()
    )

    fun diskIO(): Executor {
        return diskIO
    }

    fun networkIO(): Executor {
        return networkIO
    }

    fun mainThread(): Executor {
        return mainThread
    }

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: AppExecutors? = null

        fun getInstance(diskIO: Executor = Executors.newSingleThreadExecutor(),
                        networkIO: Executor = Executors.newFixedThreadPool(3),
                        mainThread: Executor = MainThreadExecutor()) =
            instance ?: synchronized(this) {
                instance ?: AppExecutors(diskIO, networkIO, mainThread).also { instance = it }
            }
    }
}