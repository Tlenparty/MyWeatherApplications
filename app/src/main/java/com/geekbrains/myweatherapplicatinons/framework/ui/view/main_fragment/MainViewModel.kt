package com.geekbrains.myweatherapplicatinons.framework.ui.view.main_fragment

import android.util.Log
import androidx.lifecycle.*
import com.geekbrains.myweatherapplicatinons.model.repository.Repository
import com.geekbrains.myweatherapplicatinons.model.repository.RepositoryImpl
import com.geekbrains.myweatherapplicatinons.viewmodel.AppState
import kotlinx.coroutines.*
import java.lang.Thread.sleep

class MainViewModel(private val repository: Repository)
    : ViewModel(), LifecycleObserver, CoroutineScope by MainScope() {
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()

    fun getLiveData() = liveDataToObserve

    fun getWeatherFromLocalSourceRus() = getDataFromLocalSource(isRussian = true)

    fun getWeatherFromLocalSourceWorld() = getDataFromLocalSource(isRussian = false)

    fun getWeatherFromRemoteSource() = getDataFromLocalSource(isRussian = true)

    private fun getDataFromLocalSource(isRussian: Boolean) {
        liveDataToObserve.value = AppState.Loading
        launch {
            delay(1000)
            val localStorageJob = async(Dispatchers.IO) {
                if (isRussian) repository.getWeatherFromLocalStorageRus()
                else repository.getWeatherFromLocalStorageWorld()
            }
            liveDataToObserve.value = AppState.Success(localStorageJob.await())
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onViewStart() {
        Log.i("LifecycleEvent", "onStart")
    }
}