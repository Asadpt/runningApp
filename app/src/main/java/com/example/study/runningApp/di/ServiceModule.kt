package com.example.study.runningApp.di

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.study.MainActivity
import com.example.study.R
import com.example.study.runningApp.Constant
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped
import dagger.hilt.internal.processedrootsentinel.ProcessedRootSentinel


@Module
@InstallIn(ServiceComponent::class)
object ServiceModule {

    @ServiceScoped
    @Provides
    fun provideFusedLocationProviderClient(@ApplicationContext context: Context) = FusedLocationProviderClient(context)


    @ServiceScoped
    @Provides
    fun provideMainActivityPendingIntent(@ApplicationContext context: Context) =
        PendingIntent.getActivity(context,0,Intent(context,MainActivity::class.java).also {
            it.action = Constant.ACTION_SHOW_TRACKING_FRAGMENT

        }, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)



    @ServiceScoped
    @Provides
    fun provideBaseNotificationBuilder(@ApplicationContext context: Context,pendingIntent: PendingIntent) =
        NotificationCompat.Builder(context, Constant.NOTIFICATION_CHANNEL_ID)
            .setAutoCancel(false)
            .setOngoing(true)
            .setSmallIcon(R.drawable.googleg_standard_color_18)
            .setContentTitle("Running App")
            .setContentText("00:00:00")
            .setContentIntent(pendingIntent)


}