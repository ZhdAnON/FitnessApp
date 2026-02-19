package com.zhdanon.fitnessapp.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.zhdanon.fitnessapp.data.datastore.TokenStorageImpl
import com.zhdanon.fitnessapp.domain.datastore.TokenStorage
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import java.io.File

val dataStoreModule = module {
    single<DataStore<Preferences>> {
        PreferenceDataStoreFactory.create(
            produceFile = { File(androidContext().filesDir, "tokens.preferences_pb") }
        )
    }

    single<TokenStorage> { TokenStorageImpl(get()) }
}