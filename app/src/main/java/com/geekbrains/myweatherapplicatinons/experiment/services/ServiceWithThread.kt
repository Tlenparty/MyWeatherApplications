package com.geekbrains.myweatherapplicatinons.experiment.services

import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService

class ServiceWithThread : JobIntentService() {
    override fun onHandleWork(intent: Intent) {
        //ФОНОВЫЙ ПОТОК!
        println("JOB SERVICE WORK IN THREAD")
        sendMyBroadcast()
    }
    // метод отправки сообщений в систему
    // обмен приложениями, системой или внутри одного приложения
    // можно поймать уведомление от системв или от другого прилоджения
    // отпрпвить уведомление из нашего приложения самому себе
    // сервисы не обмениваютя даннымы и иногда данные надо отдать наверх
    private fun sendMyBroadcast() {
        val broadcastIntent = Intent() // создадим intent
        broadcastIntent.putExtra(INTENT_SERVICE_DATA, true) // кладем данные по ключу
        broadcastIntent.action = INTENT_ACTION_KEY
        sendBroadcast(broadcastIntent) // отправляем сообщение
    }

    companion object {
        // данные (ключ)
        const val INTENT_ACTION_KEY = "com.geekbrains.myweatherapplicatinons.SERVICE_FINISHED_EVENT" // ключь сообщения
        const val INTENT_SERVICE_DATA = "INTENT_SERVICE_DATA" // ключь по которому положим данные

        fun start(context: Context) {
            // Запуск немного иначе. Нужен context и intent
            val intent = Intent(context, ServiceWithThread::class.java)
            enqueueWork(context, ServiceWithThread::class.java, 3322, intent)
        }
    }
}