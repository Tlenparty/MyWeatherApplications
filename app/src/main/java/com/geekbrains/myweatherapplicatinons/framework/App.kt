package com.geekbrains.myweatherapplicatinons.framework

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
        }
    }

    companion object {
        lateinit var appInstance: App
    }
}