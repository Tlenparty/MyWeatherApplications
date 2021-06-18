package com.geekbrains.myweatherapplicatinons.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer

import com.geekbrains.myweatherapplicatinons.R
import com.geekbrains.myweatherapplicatinons.databinding.MainFragmentBinding
import com.geekbrains.myweatherapplicatinons.model.Weather
import com.geekbrains.myweatherapplicatinons.viewmodel.AppState
import com.geekbrains.myweatherapplicatinons.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    // Чтобы вью не утекла
    private var _binding: MainFragmentBinding? = null

    //Обратите внимание, что эта переменная существует только между методами onCreateView и onDestroyView.
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = MainFragment()
    }

/*     //суть этого метода, чтобы избавиться от null
    private val _binding: MainFragmentBinding
        get():MainFragmentBinding = binding!!*/


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // через this привязываемся к жизненному цилку фрагмента :: чтобы обратиться к классу
        // owner  это интерфейс, который привзяывает хранилище view к компоненту
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // создали инстанс Observer,который выполняет renderData как только LiveData обновляет данные
        val observer = Observer<AppState> { renderData(it) } // it аргумент liveData
        // getData у viewModel возвр liveData
        // если данные которые хранит liveData изменятся то Observer об этом узнает и вызывет
        // метод renderData куда передаст новые данные
        viewModel
            .liveData
            .observe(viewLifecycleOwner, observer)
        viewModel.getWeatherFromLocalSource()
    }

    private fun renderData(appState: AppState) = with(binding) {
        Toast.makeText(context, "data", Toast.LENGTH_SHORT).show()
        when (appState) {
            is AppState.Error -> {
                loadingLayout.visibility = View.GONE
                Snackbar
                    .make(binding.mainView,"Error", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Reload"){
                        viewModel.getWeatherFromLocalSource()
                    }
                    .show()
            }
            AppState.Loading -> {
                loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Success -> {
                val weatherData = appState.weatherData
                loadingLayout.visibility = View.GONE
                setData(weatherData)
            }
        }
    }

    private fun setData(weatherData: Weather) = with(binding) {
        this.cityName.text = weatherData.city.city
        this.cityCoordinates.text = String.format(
            getString(R.string.city_coordinates),
            weatherData.city.lat.toString(),
            weatherData.city.lon.toString()
        )
        temperatureValue.text = weatherData.temperature.toString()
        feelsLikeValue.text = weatherData.feelsLike.toString()
    }
}