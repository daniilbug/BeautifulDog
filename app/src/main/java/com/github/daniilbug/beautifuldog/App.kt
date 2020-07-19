package com.github.daniilbug.beautifuldog

import android.app.Application
import com.github.daniilbug.beautifuldog.di.KoinWrapper
import com.github.daniilbug.beautifuldog.viewmodel.AndroidFullPhotoViewModel
import com.github.daniilbug.beautifuldog.viewmodel.AndroidLikedDogsViewModel
import com.github.daniilbug.beautifuldog.viewmodel.AndroidRandomDogViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalCoroutinesApi
class App: Application() {

    override fun onCreate() {
        super.onCreate()
        val androidViewModelModule = module {
            viewModel { AndroidRandomDogViewModel(get()) }
            viewModel { AndroidLikedDogsViewModel(get()) }
            viewModel { AndroidFullPhotoViewModel(get()) }
        }

        KoinWrapper.initKoin {
            androidContext(this@App)
            modules(androidViewModelModule)
        }
    }
}