package com.geekbrains.myweatherapplicatinons.framework.ui.view.details_fragment

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekbrains.myweatherapplicatinons.model.repository.Repository
import com.geekbrains.myweatherapplicatinons.viewmodel.AppState

class DetailsViewModel (private val repository: Repository) : ViewModel(), LifecycleObserver {
    val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()
    // метод который будет грузить данные
    fun loadData(lat: Double, lng: Double) {
        liveDataToObserve.value = AppState.Loading
        // так как не можем грузить в потоке UI нужно создать новый поток
        Thread {
            val data = repository.getWeatherFromServer(lat, lng)
            // postValue - синхронизация с потоком UI
            liveDataToObserve.postValue(AppState.Success(listOf(data)))
        }.start()
    }
}