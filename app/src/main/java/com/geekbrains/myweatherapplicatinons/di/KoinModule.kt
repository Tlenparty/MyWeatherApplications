package com.geekbrains.myweatherapplicatinons.di

import com.geekbrains.myweatherapplicatinons.framework.ui.view.details_fragment.DetailsViewModel
import com.geekbrains.myweatherapplicatinons.framework.ui.view.history_fragment.HistoryViewModel
import com.geekbrains.myweatherapplicatinons.framework.ui.view.main_fragment.MainViewModel
import com.geekbrains.myweatherapplicatinons.model.repository.Repository
import com.geekbrains.myweatherapplicatinons.model.repository.RepositoryImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<Repository> { RepositoryImpl() }
    viewModel { MainViewModel(get()) }
    viewModel { DetailsViewModel(get()) }
    viewModel { HistoryViewModel(get()) }
}