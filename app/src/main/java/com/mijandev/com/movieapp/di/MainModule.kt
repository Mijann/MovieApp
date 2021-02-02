package com.mijandev.com.movieapp.di

import com.mijandev.com.movieapp.ui.viewmodel.MainViewModel
import org.koin.dsl.module

/**
 * Created by Mohammad Hamizan on 31/1/2021.
 */
/**
 * Main Module
 * Inject Main ViewModel
 */
val mainModule = module {
    single {
        MainViewModel(get())
    }
}