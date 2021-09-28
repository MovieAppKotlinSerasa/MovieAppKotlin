package com.example.movieapp

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.work.*
import com.example.movieapp.services.NotificationHandler
import com.example.movieapp.services.NotificationWorkManager
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class MovieAppApplication : Application(), LifecycleObserver {

    @Inject
    lateinit var notificationHandler: NotificationHandler

    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onAppDestroy() {
        startWork()
    }

    private fun startWork() {
        WorkManager.getInstance(applicationContext).let {
            val workManager = WorkManager.getInstance(applicationContext)

            val constraints = Constraints.Builder()
                .setRequiresCharging(false)
                .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                .setRequiresBatteryNotLow(true)
                .build()

            val periodicWorkRequest = PeriodicWorkRequestBuilder<NotificationWorkManager>(
                15,
                TimeUnit.MINUTES
            ).setConstraints(constraints).build()

            workManager.enqueueUniquePeriodicWork(
                UUID.randomUUID().toString(),
                ExistingPeriodicWorkPolicy.KEEP,
                periodicWorkRequest
            )
        }

    }
}