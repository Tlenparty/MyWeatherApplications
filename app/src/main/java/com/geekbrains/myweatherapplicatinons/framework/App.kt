package com.geekbrains.myweatherapplicatinons.framework

import android.app.Application
import com.geekbrains.myweatherapplicatinons.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        appInstance = this
        startKoin {
            androidContext(this@App)
            modules(appModule)
        }
    }

    companion object {
        lateinit var appInstance: App
    }
}