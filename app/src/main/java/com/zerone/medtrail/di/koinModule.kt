package com.zerone.medtrail.di

import com.zerone.medtrail.*
import com.zerone.medtrail.data.MedTrailNetworkService
import com.zerone.medtrail.data.Networking
import com.zerone.medtrail.ui.main.MainViewModel
import com.zerone.medtrail.ui.main.PhotoAdapter
import com.zerone.medtrail.util.NetworkHelper
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val koinModule = module {

    fun provideMedtrailNetworkService(): MedTrailNetworkService = Networking.createNetworkCall()

    single { provideMedtrailNetworkService() }
    single { MedtrailRepository(get()) }
    single { PhotoAdapter() }
    single { NetworkHelper(androidContext()) }

    viewModel {
        MainViewModel(get())
    }

}