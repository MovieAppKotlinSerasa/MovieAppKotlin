package com.example.movieapp.services

import android.content.Context
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class NotificationWorkManager(context: Context, workParameter : WorkerParameters): Worker(context, workParameter) {

    private val notificationHandler: NotificationHandler = NotificationHandler(applicationContext)

    override fun doWork(): Result {

        notificationHandler.createNotification(
            "Qual filme você assistiu recentemente?",
            "Estamos sentindo sua falta! Venha conferir as novidades!"
        ).let {
            NotificationManagerCompat.from(applicationContext)
                .notify(2, it)
        }
        return Result.success()
    }
}