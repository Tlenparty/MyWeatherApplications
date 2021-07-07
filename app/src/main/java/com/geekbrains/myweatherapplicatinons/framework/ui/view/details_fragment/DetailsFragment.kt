package com.geekbrains.myweatherapplicatinons.framework.ui.view.details_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.geekbrains.myweatherapplicatinons.R
import com.geekbrains.myweatherapplicatinons.databinding.FragmentDetailsBinding
import com.geekbrains.myweatherapplicatinons.model.Weather
import com.geekbrains.myweatherapplicatinons.model.rest.WeatherRepo
import com.geekbrains.myweatherapplicatinons.model.rest.rest_entities.WeatherDTO
import com.geekbrains.myweatherapplicatinons.utils.CircleTransformation
import com.geekbrains.myweatherapplicatinons.viewmodel.AppState
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private val viewModel: DetailsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val weather = arguments?.getParcelable<Weather>(BUNDLE_EXTRA)
        // let проверяет аргумент на null если null то не зайдем
        weather?.let {
            with(binding) {
                val city = it.city
                cityName.text = city.city
                cityCoordinates.text = String.format(
                    getString(R.string.city_coordinates),
                    city.lat.toString(),
                    city.lon.toString()
                )
                viewModel.liveDataToObserve.observe(viewLifecycleOwner, { appState ->
                    when (appState) {
                        is AppState.Error -> {
                            //...
                            loadingLayout.visibility = View.GONE
                        }
                        AppState.Loading -> binding.loadingLayout.visibility = View.VISIBLE
                        is AppState.Success -> {
                            loadingLayout.visibility = View.GONE
                            temperatureValue.text = appState.weatherData[0].temperature?.toString()
                            feelsLikeValue.text = appState.weatherData[0].feelsLike?.toString()
                            weatherCondition.text = appState.weatherData[0].condition
                        }
                    }
                })
                viewModel.loadData(it.city)
                Picasso
                    .get()
                    .load("https://catherineasquithgallery.com/uploads/posts/2021-03/1614571201_2-p-gorod-na-belom-fone-2.png")
                    .transform(CircleTransformation())
                    .fit()
                    .into(imageView)
                /* WeatherRepo.api.getWeather(it.city.lat, it.city.lon)
                    .enqueue(object : Callback<WeatherDTO> {
                        override fun onResponse(
                            call: Call<WeatherDTO>,
                            response: Response<WeatherDTO>
                        ) {
                            if(response.isSuccessful) {
                                val weather = response.body()?.let {
                                    Weather(
                                        temperature = it.fact.temp,
                                        feelsLike = it.fact.feelsLike,
                                        condition = it.fact.condition
                                    )
                                } ?: Weather()
                                loadingLayout.visibility = View.GONE
                                temperatureValue.text = weather.temperature?.toString()
                                feelsLikeValue.text = weather.feelsLike?.toString()
                                weatherCondition.text = weather.condition
                            }
                        }
                        override fun onFailure(call: Call<WeatherDTO>, t: Throwable) {
                           //Запрос не прошел, или что-то другое на вашей стороне
                        }
                    })*/
            }
        }
    }

    companion object {
        private const val api_key = "91505610-2e55-4b79-a666-6c171068e2d3"
        const val BUNDLE_EXTRA = "weather"
        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}