package com.geekbrains.myweatherapplicatinons.framework.ui.view.history_fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekbrains.myweatherapplicatinons.model.repository.Repository
import com.geekbrains.myweatherapplicatinons.model.repository.RepositoryImpl
import com.geekbrains.myweatherapplicatinons.viewmodel.AppState
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