package com.jetbrains.kmpapp.di

import com.jetbrains.kmpapp.MuseumApp
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level
import org.koin.dsl.KoinConfiguration
import org.koin.dsl.koinConfiguration

internal actual val platformKonConfig: KoinConfiguration = koinConfiguration {
    androidLogger(Level.DEBUG)
    androidContext(MuseumApp.instance)
}