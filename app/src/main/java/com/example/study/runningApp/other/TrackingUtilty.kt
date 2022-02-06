package com.example.study.runningApp.other

import android.Manifest
import android.content.Context
import android.location.Location
import android.os.Build
import com.example.study.runningApp.services.polyline
import pub.devrel.easypermissions.EasyPermissions
import java.sql.Time
import java.util.concurrent.TimeUnit
import kotlin.math.min

object TrackingUtilty {

    fun hasLocationPermisions(context: Context) =
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.Q){
            EasyPermissions.hasPermissions(context,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION)
        }else{
            EasyPermissions.hasPermissions(context,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        }





    fun getFormattedStopWatchTime(ms:Long, includeMillis:Boolean = false):String{

        var milliseconds = ms
        val hours = TimeUnit.MILLISECONDS.toHours(milliseconds)
        milliseconds -=TimeUnit.HOURS.toMillis(hours)
        val minute = TimeUnit.MILLISECONDS.toMinutes(milliseconds)
        milliseconds -= TimeUnit.MINUTES.toMillis(minute)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds)

        if(!includeMillis){
            return "${if(hours<10) "0" else ""}$hours:"+
                    "${if(minute<10) "0" else ""}$minute:"+
                    "${if(seconds<10) "0" else ""}$seconds"
        }

        milliseconds -= TimeUnit.SECONDS.toMillis(seconds)
        milliseconds /= 10

        return "${if(hours<10) "0" else ""}$hours:"+
                "${if(minute<10) "0" else ""}$minute:"+
                "${if(seconds<10) "0" else ""}$seconds:"+
                "${if(milliseconds<10) "0" else ""}$milliseconds"

    }


    fun calcuatePolylineLength(polyline: polyline):Float{
        var distance = 0f
        for(i in 0..polyline.size-2){
            val pos1 = polyline[i]
            val pos2 = polyline[i+1]

            val result = FloatArray(1)
            Location.distanceBetween(pos1.latitude,pos1.longitude,pos2.latitude,pos2.longitude,result)
            distance +=result[0]
        }

        return distance
    }





}