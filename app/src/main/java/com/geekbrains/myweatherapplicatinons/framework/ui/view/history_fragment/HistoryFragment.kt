package com.geekbrains.myweatherapplicatinons.framework.ui.view.history_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.geekbrains.myweatherapplicatinons.R
import com.geekbrains.myweatherapplicatinons.databinding.FragmentHistoryBinding
import com.geekbrains.myweatherapplicatinons.experiment.showSnackBar
import com.geekbrains.myweatherapplicatinons.framework.ui.adapters.HistoryAdapter
import com.geekbrains.myweatherapplicatinons.framework.ui.view.details_fragment.DetailsViewModel
import com.geekbrains.myweatherapplicatinons.viewmodel.AppState
import kotlinx.android.synthetic.main.fragment_history.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HistoryViewModel by lazy {
        ViewModelProvider(this).get(HistoryViewModel::class.java)
    }

    private val adapter: HistoryAdapter by lazy { HistoryAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        historyFragmentRecyclerview.adapter = adapter
        viewModel.historyLiveData.observe(viewLifecycleOwner, { renderData(it) })
        viewModel.getAllHistory()
    }

    private fun renderData(appState: AppState) = with(binding) {
        when (appState) {
            is AppState.Success -> {
                historyFragmentRecyclerview.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
                adapter.setData(appState.weatherData)
            }
            is AppState.Loading -> {
                historyFragmentRecyclerview.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                historyFragmentRecyclerview.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
                historyFragmentRecyclerview.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload),
                    {
                        viewModel.getAllHistory()
                    }
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = HistoryFragment()
    }
}
