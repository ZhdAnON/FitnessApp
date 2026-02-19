package com.zhdanon.fitnessapp

import android.app.Application
import android.content.Context
import com.zhdanon.fitnessapp.di.dataStoreModule
import com.zhdanon.fitnessapp.di.domainModule
import com.zhdanon.fitnessapp.di.networkModule
import com.zhdanon.fitnessapp.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                networkModule,
                domainModule,
                presentationModule,
                dataStoreModule
            )
        }
    }

    companion object {
        lateinit var context: Context private set
    }
}