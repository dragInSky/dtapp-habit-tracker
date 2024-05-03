package com.example.dtapp

import android.app.Application

class App : Application() {
    lateinit var applicationComponent: ApplicationComponent
        private set

    override fun onCreate() {
        super.onCreate()

        instance = this

        applicationComponent = DaggerApplicationComponent.builder().contextModule(ContextModule(this)).build()
    }

    companion object {
        lateinit var instance: App
            private set
    }
}
