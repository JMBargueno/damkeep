package com.jmbargueno.damkeepapp.common

import android.app.Application

class MyApp: Application() {
    //TODO definimos el @Component para tener acceso a las dependencias en la aplicación

    companion object {
        lateinit var instance: MyApp
        lateinit var appComponent: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        appComponent =DaggerApplicationComponent.create()
    }
    fun getAppCompontent(): ApplicationComponent{
        return appComponent
    }
}