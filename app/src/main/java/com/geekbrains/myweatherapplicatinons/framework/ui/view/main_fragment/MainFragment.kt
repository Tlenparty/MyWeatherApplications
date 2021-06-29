package com.geekbrains.myweatherapplicatinons.framework.ui.view.main_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.geekbrains.myweatherapplicatinons.R
import com.geekbrains.myweatherapplicatinons.databinding.FragmentMainBinding
import com.geekbrains.myweatherapplicatinons.experiment.showSnackBar
import com.geekbrains.myweatherapplicatinons.model.Weather
import com.geekbrains.myweatherapplicatinons.framework.ui.adapters.MainFragmentAdapter
import com.geekbrains.myweatherapplicatinons.framework.ui.view.details_fragment.DetailsFragment
import com.geekbrains.myweatherapplicatinons.viewmodel.AppState
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment(), CoroutineScope by MainScope(){ // все курутины будут работать в UI thread
    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModel()

    private var adapter: MainFragmentAdapter? = null
    private var isDataSetRus: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mainFragmentRecyclerView.adapter = adapter
        binding.mainFragmentFAB.setOnClickListener { changeWeatherDataSet() }
        viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
        viewModel.getWeatherFromLocalSourceRus()
        //  launch вызов курутины
        // Dispatchers- для изменения курутины
        // .IO(фон) .Default (фон для расчетов) .Main(UI-поток) .Unconfined
        /*launch {
            delay(1000) // спим, только без зависания
            val job = async(Dispatchers.Default) { // launch только с возвратом значения
                startCalculations(10)
            }
            // сначала job.await - заставит дождать окончания job и только потом идем дальше
            println(job.await())
        }*/
    }

    private fun changeWeatherDataSet() = with(binding) {
        if (isDataSetRus) {
            viewModel.getWeatherFromLocalSourceWorld()
            mainFragmentFAB.setImageResource(R.drawable.ic_earth)
        } else {
            viewModel.getWeatherFromLocalSourceRus()
            mainFragmentFAB.setImageResource(R.drawable.ic_russia)
        }
        isDataSetRus = !isDataSetRus
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                mainFragmentLoadingLayout.visibility = View.GONE
                adapter = MainFragmentAdapter(object : OnItemViewClickListener {
                    override fun onItemViewClick(weather: Weather) {
                        val manager = activity?.supportFragmentManager
                        manager?.let { manager ->
                            val bundle = Bundle().apply {
                                putParcelable(DetailsFragment.BUNDLE_EXTRA, weather)
                            }
                            manager.beginTransaction()
                                .replace(R.id.container, DetailsFragment.newInstance(bundle))
                                .addToBackStack("")
                                .commitAllowingStateLoss()
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
                    { viewModel.getWeatherFromLocalSourceRus() },

                )
            }
        }
    }

    companion object {
        fun newInstance() = MainFragment()
    }

    // В адаптере этот интрефс будет приходить на вход
    interface OnItemViewClickListener {
        fun onItemViewClick(weather: Weather)
    }

}