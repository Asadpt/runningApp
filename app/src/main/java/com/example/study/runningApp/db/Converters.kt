package com.example.study.runningApp.db

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

class Converters {

    @TypeConverter
    fun fromBitmap(bmp:Bitmap):ByteArray{
        val outputstream = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.PNG,100,outputstream)
        return outputstream.toByteArray()
    }

    @TypeConverter
    fun toBitmap(bytes:ByteArray):Bitmap{
        return BitmapFactory.decodeByteArray(bytes,0,bytes.size)
    }
}