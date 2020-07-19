package com.github.daniilbug.beautifuldog.di

import com.github.daniilbug.beautifuldog.data.DogPhotoProvider
import com.github.daniilbug.beautifuldog.data.LikedDogPhotoRepository
import com.github.daniilbug.beautifuldog.data.network.NetworkDogPhotoProvider
import com.github.daniilbug.beautifuldog.data.persistent.PersistentLikedDogPhotoRepository
import com.github.daniilbug.beautifuldog.interactors.FullPhotoInteractor
import com.github.daniilbug.beautifuldog.interactors.LikedDogsInteractor
import com.github.daniilbug.beautifuldog.interactors.RandomDogInteractor
import com.github.daniilbug.beautifuldog.multiplatform.makeModuleForDriver
import com.github.daniilbug.beautifuldog.viewmodel.fullphoto.FullPhotoViewModel
import com.github.daniilbug.beautifuldog.viewmodel.likeddogs.LikedDogsViewModel
import com.github.daniilbug.beautifuldog.viewmodel.randomdog.RandomDogViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

@ExperimentalCoroutinesApi
object KoinWrapper {
    private val dataModule = module {
        single<DogPhotoProvider> { NetworkDogPhotoProvider() }
        single<LikedDogPhotoRepository> { PersistentLikedDogPhotoRepository(get()) }
    }

    private val interactorModule = module {
        factory { RandomDogInteractor(get(), get()) }
        factory { LikedDogsInteractor(get()) }
        factory { FullPhotoInteractor(get()) }
    }

    private val viewModelModule = module {
        factory { RandomDogViewModel(get()) }
        factory { LikedDogsViewModel(get()) }
        factory { FullPhotoViewModel(get()) }
    }

    fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
        appDeclaration()
        modules(makeModuleForDriver(), dataModule, interactorModule, viewModelModule)
    }

    fun initKoin() = initKoin { }
}