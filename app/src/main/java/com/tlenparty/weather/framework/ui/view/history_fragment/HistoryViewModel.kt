package com.tlenparty.weather.framework.ui.view.history_fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tlenparty.weather.model.repository.Repository
import com.tlenparty.weather.model.repository.RepositoryImpl
import com.tlenparty.weather.viewmodel.AppState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val repository: Repository = RepositoryImpl(),
    val historyLiveData: MutableLiveData<AppState> = MutableLiveData()

) : ViewModel(), CoroutineScope by MainScope() {

    fun getAllHistory() {
        historyLiveData.value = AppState.Loading
        launch(Dispatchers.IO) {
            historyLiveData.postValue(AppState.Success(repository.getAllHistory()))
        }
    }
}