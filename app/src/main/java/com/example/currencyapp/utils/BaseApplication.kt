package com.example.currencyapp.utils

import android.app.Application
import com.example.currencyapp.local.SessionManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        SessionManager(applicationContext)
    }
}