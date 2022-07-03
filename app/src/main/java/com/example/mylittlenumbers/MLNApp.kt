package com.example.mylittlenumbers

import android.app.Application
import timber.log.Timber

class MLNApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }
}