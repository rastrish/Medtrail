package com.zerone.medtrail

import android.app.Application
import com.zerone.medtrail.di.koinModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MedtrailApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MedtrailApplication)
            modules(koinModule)
        }
    }

}