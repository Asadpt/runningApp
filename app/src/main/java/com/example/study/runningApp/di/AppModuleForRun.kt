package com.example.study.runningApp.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.provider.DocumentsContract
import androidx.room.Room
import com.example.study.runningApp.Constant.KEY_FIRST_TIME_TOGGLE
import com.example.study.runningApp.Constant.KEY_NAME
import com.example.study.runningApp.Constant.KEY_WEIGHT
import com.example.study.runningApp.Constant.RUNNING_DATABSE_NAME
import com.example.study.runningApp.Constant.SHARED_PREFERENCE_NAME
import com.example.study.runningApp.db.RunningDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModuleForRun {


    @Provides
    @Singleton
    fun provideRunningDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context,RunningDatabase::class.java,RUNNING_DATABSE_NAME).build()


    @Provides
    @Singleton
    fun provideRunDao(db:RunningDatabase) = db.getRunDao()

    @Provides
    @Singleton
    fun providesSharedPreference(@ApplicationContext context: Context) =
        context.getSharedPreferences(SHARED_PREFERENCE_NAME,MODE_PRIVATE)



    @Provides
    @Singleton
    fun provideWeight(sharedPref:SharedPreferences) = sharedPref.getFloat(KEY_WEIGHT,80f)

    @Provides
    @Singleton
    fun provideFirsttimeToggle(sharedPref:SharedPreferences) = sharedPref.getBoolean(
        KEY_FIRST_TIME_TOGGLE,true)


}