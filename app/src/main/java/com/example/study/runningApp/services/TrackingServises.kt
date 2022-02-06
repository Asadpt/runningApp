package com.example.study.runningApp.services

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationRequest
import android.os.Build
import android.os.Looper
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.withStarted
import com.example.study.R
import com.example.study.runningApp.Constant.ACTION_PAUSE_SERVICE
import com.example.study.runningApp.Constant.ACTION_SHOW_TRACKING_FRAGMENT
import com.example.study.runningApp.Constant.ACTION_START_OR_RESUME_SERVICE
import com.example.study.runningApp.Constant.ACTION_STOP_SERVICE
import com.example.study.runningApp.Constant.FASTEST_LOCATION_INTERVAL
import com.example.study.runningApp.Constant.LOCATION_UPDATE_INTERVAL
import com.example.study.runningApp.Constant.NOTIFICATION_CHANNEL_ID
import com.example.study.runningApp.Constant.NOTIFICATION_CHANNEL_NAME
import com.example.study.runningApp.Constant.NOTIFICATION_ID
import com.example.study.runningApp.Constant.TIMER_UPDATE_INTERVAl
import com.example.study.runningApp.other.TrackingUtilty
import com.example.study.runningApp.ui.MainActivityRunning
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Polyline
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


typealias polyline = MutableList<LatLng>
typealias polylines = MutableList<polyline>

@AndroidEntryPoint
class TrackingServises:LifecycleService() {

    var firstRun = true

    @Inject
    lateinit var fusedLocationProviderClient:FusedLocationProviderClient

    @Inject
    lateinit var baseNotificationBuilder:NotificationCompat.Builder


    private lateinit var curNotificationBuilder:NotificationCompat.Builder


    private val timesRunInSeconds = MutableLiveData<Long>()

    override fun onCreate() {
        super.onCreate()
        postInitialValues()
        curNotificationBuilder = baseNotificationBuilder

        // DI added

//        fusedLocationProviderClient = FusedLocationProviderClient(this)



        isTracking.observe(this, Observer {
            updateLocationTracking(it)
            updateNotificationTrackingState(it)
        })
    }

    companion object{
        val isTracking = MutableLiveData<Boolean>()

        //this is because of several poly lines
        val pathPoints = MutableLiveData<polylines>()
        val timesRunInMilliSeconds = MutableLiveData<Long>()
    }

    private fun postInitialValues(){
        isTracking.postValue(false)
        pathPoints?.postValue(mutableListOf())

        timesRunInSeconds?.postValue(0L)
        timesRunInMilliSeconds.postValue(0L)
    }

    private fun addEmptyPolyline(){
        pathPoints?.postValue(mutableListOf(mutableListOf()))

    }

    private fun addPathPoint(location:Location?){
        location?.let {
            val pos = LatLng(location.latitude,location.longitude)
            pathPoints.value?.apply {
                last().add(pos)
                pathPoints.postValue(this)
            }
        }
    }

    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            if(isTracking.value!!){
                locationResult?.locations?.let {
                    for(location in it){
                        addPathPoint(location)
                        Timber.d("NEW LOCATION: ${location.latitude},${location.longitude}")
                    }
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun updateLocationTracking(isTracking:Boolean){
        if(isTracking){
            if(TrackingUtilty.hasLocationPermisions(this)){
               val request = com.google.android.gms.location.LocationRequest.create().apply {
                   interval = LOCATION_UPDATE_INTERVAL
                   fastestInterval = FASTEST_LOCATION_INTERVAL
                   priority = PRIORITY_HIGH_ACCURACY
               }

                fusedLocationProviderClient.requestLocationUpdates(
                    request,locationCallback, Looper.getMainLooper()
                )
            }
        }else{
            fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        }
    }





    /*
    this is called when we send an intent with action attached to service
    we have three action
    action to start/reume
    action to pause
    action to stop
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        intent?.let {
            when(it.action){
                ACTION_START_OR_RESUME_SERVICE ->{
                    if(firstRun){
                        startForegroundService()
                        firstRun = false
                    }else{
                        Timber.d("Resuming service")
                        startTimer()
                    }


                }
                ACTION_PAUSE_SERVICE ->{
                    Timber.d("Paused service")
                    pauseService()
                }
                ACTION_STOP_SERVICE ->{
                    Timber.d("Stopped service")
                    kilService()
                }
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager){

        val notificationchannel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW)

        notificationManager.createNotificationChannel(notificationchannel)

    }

    private fun startForegroundService(){
        startTimer()
        isTracking.postValue(true)

        val notificationMAnger = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            createNotificationChannel(notificationMAnger)


        //DI added

//        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
//            .setAutoCancel(false)
//            .setOngoing(true)
//            .setSmallIcon(R.drawable.googleg_standard_color_18)
//            .setContentTitle("Running App")
//            .setContentText("00:00:00")
//            .setContentIntent(getMainActivityPendingIntent())

        startForeground(NOTIFICATION_ID,baseNotificationBuilder.build())

        timesRunInSeconds.observe(this, Observer {
            if(!serviceKilled){
                val notification =  curNotificationBuilder.setContentText(TrackingUtilty.getFormattedStopWatchTime(it.times(1000),false))
                notificationMAnger.notify(NOTIFICATION_ID,notification.build())
            }

        })
    }

    //DI added

//    private fun getMainActivityPendingIntent() = PendingIntent.getActivity(
//        this,0, Intent(this,MainActivityRunning::class.java).also {
//            it.action = ACTION_SHOW_TRACKING_FRAGMENT
//
//        },PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
//    )

    private fun pauseService(){
        isTracking.postValue(false)
        isTimerEnabled = false
    }

    private var isTimerEnabled = false
    // from when the timer is start
    private var lapTime = 0L

    //all our laptime is added together here
    private var timeRun = 0L

    //ti,e stamp when we start the time
    private var timeStarted = 0L

    private var lastSecondTimeStamp = 0L

    private fun startTimer(){
        addEmptyPolyline()
        isTracking.postValue(true)
        timeStarted = System.currentTimeMillis()
        isTimerEnabled = true

        CoroutineScope(Dispatchers.Main).launch {
            while(isTracking.value!!){
                lapTime = System.currentTimeMillis() - timeStarted
                        timesRunInMilliSeconds.postValue(timeRun+lapTime)
                    if(timesRunInMilliSeconds.value!! >= lastSecondTimeStamp + 1000L){
                    timesRunInSeconds.postValue(timesRunInSeconds.value!!+1)
                    lastSecondTimeStamp += 1000L
                }
                delay(TIMER_UPDATE_INTERVAl)
            }
            timeRun += lapTime

        }


    }

    private fun updateNotificationTrackingState(isTracking: Boolean){

        val notificationActionText = if(isTracking) "pause" else "resume"

        val pendingIntent = if(isTracking)
        {
            val pauseIntent = Intent(this,TrackingServises::class.java).apply {
                action = ACTION_PAUSE_SERVICE
            }
            PendingIntent.getService(this,1,pauseIntent,PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        }else{
            val resumeIntent = Intent(this,TrackingServises::class.java).apply {
                action = ACTION_START_OR_RESUME_SERVICE
            }
            PendingIntent.getService(this,2,resumeIntent,PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        }


        val notificationManger = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        curNotificationBuilder.javaClass.getDeclaredField("mActions").apply {
            isAccessible = true
            set(curNotificationBuilder,ArrayList<NotificationCompat.Action>())

        }

        if(!serviceKilled){
            curNotificationBuilder = baseNotificationBuilder.addAction(R.drawable.common_google_signin_btn_icon_dark,notificationActionText,pendingIntent)
            notificationManger.notify(NOTIFICATION_ID,curNotificationBuilder.build())
        }




    }

    var serviceKilled =  false
    private fun kilService(){
        serviceKilled = true
        firstRun = true
        pauseService()
        postInitialValues()
        stopForeground(true)
        stopSelf()
    }



}