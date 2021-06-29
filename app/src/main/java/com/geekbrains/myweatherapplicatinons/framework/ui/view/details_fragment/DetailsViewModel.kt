package com.geekbrains.myweatherapplicatinons.framework.ui.view.details_fragment

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekbrains.myweatherapplicatinons.model.repository.Repository
import com.geekbrains.myweatherapplicatinons.viewmodel.AppState
import kotlinx.coroutines.*

class DetailsViewModel (private val repository: Repository)
    : ViewModel(), LifecycleObserver,CoroutineScope by MainScope() {
    val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()

    fun loadData(lat: Double, lng: Double) {
        liveDataToObserve.value = AppState.Loading
        launch {
            val job = async(Dispatchers.IO) { repository.getWeatherFromServer(lat, lng) }
            liveDataToObserve.value = AppState.Success(listOf(job.await()))
        }
    }
}