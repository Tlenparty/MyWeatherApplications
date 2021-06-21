package com.geekbrains.myweatherapplicatinons.framework.ui.view.details_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.geekbrains.myweatherapplicatinons.R
import com.geekbrains.myweatherapplicatinons.databinding.FragmentDetailsBinding
import com.geekbrains.myweatherapplicatinons.model.Weather


class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding

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
            // it - это Weather
            // берем навзание города
            val city = it.city
            binding.cityName.text = city.city
            binding.cityCoordinates.text = String.format(
                getString(R.string.city_coordinates),
                city.lat.toString(),
                city.lon.toString()
            )
            binding.temperatureValue.text = it.temperature.toString()
            binding.feelsLikeLabel.text = it.feelsLike.toString()
        } //?: run { // сode..} если хотим дальше запустить код
    }

    /* Можно обращаться к методам и свойствам объекта через имя содержащего его класса без явного указания
   имени объекта.*/
    companion object {
        const val BUNDLE_EXTRA = "weather" // const - означет, что переменная будет компилирована

        // в константу на этапе компиляции
        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}