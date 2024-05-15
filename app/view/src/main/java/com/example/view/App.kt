package com.example.view

import android.app.Application
import com.example.view.di.AppComponent
import com.example.view.di.ContextModule
import com.example.view.di.DaggerAppComponent

class App : Application() {
    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()

        instance = this

        appComponent = DaggerAppComponent.builder().contextModule(
            ContextModule(this)
        ).build()
    }

    companion object {
        lateinit var instance: App
            private set
    }
}
