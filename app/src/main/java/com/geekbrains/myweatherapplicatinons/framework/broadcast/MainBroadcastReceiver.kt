package com.geekbrains.myweatherapplicatinons.framework.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
// Данный ресивер мы должны зарегистрировать в манифесте
class MainBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        StringBuilder().apply {
            append("СООБЩЕНИЕ ОТ СИСТЕМЫ\n")
            append("Action: ${intent.action}")
            toString().also {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        }
    }
}