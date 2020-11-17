package com.eggy.githubuserapp.helper

import android.app.Application
import com.facebook.stetho.Stetho

class App:Application() {
    override fun onCreate() {
        super.onCreate()

        Stetho.initialize(Stetho.newInitializerBuilder(this)
            .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
            .build())
    }
}