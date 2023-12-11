package com.project

import android.app.Application
import com.project.elt.di.module.AppModule
import com.project.elt.di.module.AppModuleImpl

class App: Application() {

    companion object{
        lateinit var appModule: AppModule
    }

    override fun onCreate() {
        super.onCreate()
        appModule = AppModuleImpl(this)
    }
}