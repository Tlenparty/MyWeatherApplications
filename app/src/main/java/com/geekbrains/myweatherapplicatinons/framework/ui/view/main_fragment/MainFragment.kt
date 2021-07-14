package com.geekbrains.myweatherapplicatinons.framework.ui.view.main_fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.geekbrains.myweatherapplicatinons.R
import com.geekbrains.myweatherapplicatinons.databinding.FragmentMainBinding
import com.geekbrains.myweatherapplicatinons.experiment.showSnackBar
import com.geekbrains.myweatherapplicatinons.model.Weather
import com.geekbrains.myweatherapplicatinons.framework.ui.adapters.MainFragmentAdapter
import com.geekbrains.myweatherapplicatinons.framework.ui.view.details_fragment.DetailsFragment
import com.geekbrains.myweatherapplicatinons.model.City
import com.geekbrains.myweatherapplicatinons.viewmodel.AppState
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.IOException
import java.util.jar.Manifest

class MainFragment : Fragment(), CoroutineScope by MainScope() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private var adapter: MainFragmentAdapter? = null
    private var isDataSetRus: Boolean = true

    private val permissionResult = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { result ->
        if (result) {
            getLocation()
        } else {
            Toast.makeText(
                context,
                getString(R.string.need_permissions_to_find_location),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private val onLocationListener = LocationListener { location -> getAddressAsync(location) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            mainFragmentRecyclerView.adapter = adapter
            mainFragmentFAB.setOnClickListener { changeWeatherDataSet() }
            mainFragmentFABLocation.setOnClickListener { checkPermission() }
        }
        viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
        viewModel.getWeatherFromLocalSourceRus()

        initDataSet()
        loadDataSet()
    }

    private fun initDataSet() {
        activity?.let {
            isDataSetRus = it.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
                .getBoolean(isDataSetKeyRus, true)
        }
    }

    private fun changeWeatherDataSet() {
        isDataSetRus = !isDataSetRus
        loadDataSet()
    }

    private fun loadDataSet() = with(binding) {
        if (isDataSetRus) {
            viewModel.getWeatherFromLocalSourceWorld()
            mainFragmentFAB.setImageResource(R.drawable.ic_earth)
        } else {
            viewModel.getWeatherFromLocalSourceRus()
            mainFragmentFAB.setImageResource(R.drawable.ic_russia)
        }
        saveDataToDisk()
    }

    private fun saveDataToDisk() {
        activity?.let {
            val preferences = it.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.putBoolean(isDataSetKeyRus, isDataSetRus)
            editor.apply()
        }
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                mainFragmentLoadingLayout.visibility = View.GONE
                adapter = MainFragmentAdapter(object : OnItemViewClickListener {
                    override fun onItemViewClick(weather: Weather) {
                        val manager = activity?.supportFragmentManager
                        manager?.let { manager ->
                            openDetailsFragment(weather)
                        }
                    }
                }
                ).apply {
                    setWeather(appState.weatherData)
                }
                mainFragmentRecyclerView.adapter = adapter
            }
            is AppState.Loading -> {
                mainFragmentLoadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                mainFragmentLoadingLayout.visibility = View.GONE
                mainFragmentFAB.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload),
                    {
                        viewModel.getWeatherFromLocalSourceRus()
                    },

                    )
            }
        }
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            getLocation()
        } else {
            permissionResult.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        val locationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            val provider = locationManager.getProvider(LocationManager.GPS_PROVIDER)
            provider?.let {
                // Будем получать геоположение через каждый 60 секунд (ч/з подписку)
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    5000L,
                    10f,
                    onLocationListener
                )
            }
        } else {
            // Запрос последнего местопололжения разово
            val location =
                locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (location == null) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.looks_like_location_disabled),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                getAddressAsync(location)
            }
        }
    }

    private fun getAddressAsync(location: Location) = with(binding) {
        val geoCoder = Geocoder(context) // заправшиваем геокодер
        // Поток потому, что выход в интрнет
        launch(Dispatchers.IO) {
            try {
                val addresses = geoCoder.getFromLocation(
                    location.latitude,
                    location.latitude,
                    1
                )
                mainFragmentFAB.post {
                    showAddressDialog(addresses[0].getAddressLine(0), location)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun showAddressDialog(address: String, location: Location) {
        activity?.let {
            AlertDialog.Builder(it)
                .setTitle(getString(R.string.dialog_address_title))
                .setMessage(address)
                .setPositiveButton(getString(R.string.dialog_address_get_weather)) { _, _ -> // по нажатию ок открываем фрагмент
                    openDetailsFragment(
                        Weather(
                            City(
                                // Получаем в режиме реального времени
                                address,
                                location.latitude,
                                location.longitude
                            )
                        )
                    )
                }
                .setNegativeButton(getString(R.string.dialog_button_close)) { dialog, _ -> dialog.dismiss() }
                .create()
                .show()
        }
    }

    private fun openDetailsFragment(weather: Weather) {
        activity?.supportFragmentManager?.let { manager ->
            val bundle = Bundle().apply {
                putParcelable(DetailsFragment.BUNDLE_EXTRA, weather)
            }
            manager.beginTransaction()
                .add(R.id.container, DetailsFragment.newInstance(bundle))
                .addToBackStack("")
                .commitAllowingStateLoss()
        }
    }

    companion object {
        fun newInstance() = MainFragment()
        private const val isDataSetKeyRus = "isDataSetKeyRus"
        private const val preferencesName = "MainPreferences"
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(weather: Weather)
    }

}