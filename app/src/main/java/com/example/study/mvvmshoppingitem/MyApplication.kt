package com.example.study.mvvmshoppingitem

import android.app.Application
import com.example.study.mvvmshoppingitem.di.AppContainer
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }















//     lateinit var appContainer:AppContainer
//
//    override fun onCreate() {
//        super.onCreate()
//        appContainer = AppContainer(this)
//    }










}