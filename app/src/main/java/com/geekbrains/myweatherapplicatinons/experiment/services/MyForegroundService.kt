package com.geekbrains.myweatherapplicatinons.experiment.services

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.geekbrains.myweatherapplicatinons.R

class MyForegroundService: Service() {
    // У сервиса есть onCreate
    // Тут подготавливаем иконку
    override fun onCreate() {
        super.onCreate()
        // Как только включаем
        createNotificationChannel()

        val launchIntent = packageManager.getLaunchIntentForPackage(packageName)
        val stackBuilder: TaskStackBuilder = TaskStackBuilder.create(this)
        stackBuilder.addNextIntentWithParentStack(launchIntent)
        val pendingIntent = stackBuilder.getPendingIntent(
            0,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(this, "some_channel")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setSound(null)
            .build()

        startForeground(12345, notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun createNotificationChannel() {
        // Если версия больше, чем Oreo
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Уведомление как активируем экран смартфога
                // Постепенные уведомления IMPORTANCE_DEFAULT
                    // LOW - внизу шторки уведомлений
            val notificationChannel = // системный сервис
                NotificationChannel(
                    "some_channel",
                    "Progress",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    companion object {
        fun start(context: Context) {
            val usualServiceIntent = Intent(context, MyForegroundService::class.java)
            context.startService(usualServiceIntent)
        }

        fun stop(context: Context) {
            val usualServiceIntent = Intent(context, MyForegroundService::class.java)
            context.stopService(usualServiceIntent)
        }
    }
}
