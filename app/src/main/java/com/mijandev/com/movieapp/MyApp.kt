package com.mijandev.com.movieapp

import android.app.Application
import com.mijandev.com.movieapp.core.di.coreDataModule
import com.mijandev.com.movieapp.di.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Created by Mohammad Hamizan on 31/1/2021.
 */
/**
 * MyApp Application class
 * Start Koin configuration
 * Inject Core Data module and Main module
 */
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            modules(listOf(coreDataModule, mainModule))
        }
    }
}