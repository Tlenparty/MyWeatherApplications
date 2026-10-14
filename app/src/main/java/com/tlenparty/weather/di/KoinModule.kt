package com.tlenparty.weather.di

import com.tlenparty.weather.framework.ui.view.details_fragment.DetailsViewModel
import com.tlenparty.weather.framework.ui.view.history_fragment.HistoryViewModel
import com.tlenparty.weather.framework.ui.view.main_fragment.MainViewModel
import com.tlenparty.weather.model.repository.Repository
import com.tlenparty.weather.model.repository.RepositoryImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<Repository> { RepositoryImpl() }
    viewModel { MainViewModel(get()) }
    viewModel { DetailsViewModel(get()) }
    viewModel { HistoryViewModel(get()) }
}