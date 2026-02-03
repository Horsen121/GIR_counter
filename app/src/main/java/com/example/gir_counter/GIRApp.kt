package com.example.gir_counter

import android.app.Application
import com.example.gir_count.GIRCounterModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class GIRApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@GIRApp)

            modules(
                GIRCounterModule
            )
        }
    }
}