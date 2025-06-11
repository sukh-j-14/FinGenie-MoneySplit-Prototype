package com.fingenie

import android.app.Application
import com.google.firebase.FirebaseApp

class FingenieApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
} 