package com.geekbrains.myweatherapplicatinons.framework.ui.view.main_fragment

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekbrains.myweatherapplicatinons.model.repository.Repository
import com.geekbrains.myweatherapplicatinons.model.repository.RepositoryImpl
import com.geekbrains.myweatherapplicatinons.viewmodel.AppState
import kotlinx.coroutines.*
import java.lang.Thread.sleep

/**
View Model снабжает всеми необходими данными UI и сохраняет текущее состояние экрана в процессе
пересоздания UI.
В нашем случае UI подписывается на изменение данных через LiveData.
1 Все экземпляры ViewModel хранятся в аналоге retainFragment, который, как и обычный фрагмент,
умеет переживать пересоздание Activity, а значит, сохраняет все модели с данными.
2 Класс ViewModel хранит все экземпляры LiveData, чтобы они переживали пересоздание Activity.
3 Связь ViewModel с View осуществляется через LiveData, то есть ViewModel не управляет View напрямую.

LiveData применяется, чтобы не хранить ссылки на Activity или Fragment во ViewModel,
потому что хранение ссылок на эти классы, даже в виде интерфейсов, как в MVP, — не лучшее решение.
LiveData представляет собой реализацию паттерна Observable. Мы можем сохранять данные в LiveData
через метод setValue(), если делаем это с главного потока приложения, или postValue(),
когда хотим передать данные из другого потока.
 */
class MainViewModel(
    private val repository: Repository
) : ViewModel(), LifecycleObserver,CoroutineScope by MainScope() {

    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()

    // liveData внутри содержит AppState
    fun getLiveData() = liveDataToObserve

    fun getWeatherFromLocalSourceRus() = getDataFromLocalSource(isRussian = true)

    fun getWeatherFromLocalSourceWorld() = getDataFromLocalSource(isRussian = false)

    fun getWeatherFromRemoteSource() = getDataFromLocalSource(isRussian = true)

    fun getDataFromLocalSource(isRussian: Boolean) {
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
}
/**
 * В классе LiveData доступны методы setValue и postValue: первый метод для обновления данных из
 * основного потока, второй — из рабочего потока. Есть также открытый метод getData,
 * который возвращает нашу LiveData всем,кто хочет подписаться на изменения данных.
 * Тип объекта, хранящий в себе LiveData, — Any (в качестве примера).
 */