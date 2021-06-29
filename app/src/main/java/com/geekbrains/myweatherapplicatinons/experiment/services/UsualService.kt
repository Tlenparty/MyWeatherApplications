package com.geekbrains.myweatherapplicatinons.experiment.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder

class UsualService : Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Наш код сервиса. Здесь поток UI
        // Запускаем свой собственный фоновый поток
        Thread {
            ///...
            // чтобы сервис остановить
            stopSelf()
        }.start()
        // флаг если сревис умерт, значит умрет
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    // Запуск сервиса
    companion object {
        fun start(context: Context) {
            val usualServiceIntent = Intent(context, UsualService::class.java)
            context.startService(usualServiceIntent)
        }
    // Остановка сервиса
        fun stop(context: Context) {
            val usualServiceIntent = Intent(context, UsualService::class.java)
            context.stopService(usualServiceIntent)
        }
    }
}